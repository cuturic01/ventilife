import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {

  let authToken = localStorage.getItem('user');
  let accessToken = undefined
  if (authToken) {
    const tokenObject = JSON.parse(authToken);
    accessToken = tokenObject.accessToken;
  }

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${accessToken}`
    }
  });
  return next(authReq);
};
