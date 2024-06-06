import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {LoginCredentials, UserRegistrationData} from "../model";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {JwtHelperService} from "@auth0/angular-jwt";
import {DOCUMENT, isPlatformBrowser} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
  });

  userLogged$ = new BehaviorSubject<any>(null);
  userLoggedState$ = this.userLogged$.asObservable();

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: any) {
  }

  login(credentials: LoginCredentials): Observable<string>{
    return this.http.post<string>(environment.apiHost + 'user/login', credentials);
  }

  register(user: UserRegistrationData): Observable<UserRegistrationData>{
    return this.http.post<UserRegistrationData>(environment.apiHost + "user/register", user);
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('user') != null;
    }
    return false;
  }

  setUserLogged(): void {
    this.userLogged$.next(this.getRole());
  }

  getRole(): any {
    if (this.isLoggedIn() && isPlatformBrowser(this.platformId)) {
      const accessTokenString: any = localStorage.getItem('user');
      const accessToken = JSON.parse(accessTokenString);
      const helper = new JwtHelperService();
      const roles = helper.decodeToken(accessToken.accessToken).role;

      const roleNames = roles.map((obj: { name: any; }) => obj.name);

      return roleNames;
    }
    return null;
  }

  getUserId(): number{
    if (this.isLoggedIn() && isPlatformBrowser(this.platformId)) {
      const accessTokenString: any = localStorage.getItem('user');
      const accessToken = JSON.parse(accessTokenString);
      const helper = new JwtHelperService();
      return helper.decodeToken(accessToken.accessToken).id;
    }
    return -1;
  }

  getUserMail(): string {
    if (this.isLoggedIn() && isPlatformBrowser(this.platformId)) {
      const accessTokenString: any = localStorage.getItem('user');
      const accessToken = JSON.parse(accessTokenString);
      const helper = new JwtHelperService();
      return helper.decodeToken(accessToken.accessToken).sub;
    }
    return "";
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('user');
      this.setUserLogged();
    }
  }

  checkTokenValidity(){
    if (this.isLoggedIn() && isPlatformBrowser(this.platformId)) {
      const accessTokenString: any = localStorage.getItem('user');
      const helper = new JwtHelperService();

      if (helper.isTokenExpired(accessTokenString)) {
        localStorage.removeItem("user");
        this.setUserLogged();
        window.location.href = "http://localhost:4200/";
      }
    }

  }
}
