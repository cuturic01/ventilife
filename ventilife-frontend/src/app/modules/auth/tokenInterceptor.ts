import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {AuthService} from "./service/auth.service";
import {Observable} from "rxjs";


@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(public auth: AuthService) { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.auth.isLoggedIn()) {
      const accessTokenString: any = localStorage.getItem('user');
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${JSON.parse(accessTokenString).accessToken}`
        }
      });
    }
    return next.handle(request);
  }
}
