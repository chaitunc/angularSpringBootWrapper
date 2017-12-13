import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { TokenService } from './token.service';

export class JwtInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.tokenService.isUserAuthenticated()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.tokenService.getToken()}`
        }
      });
    }

    return next.handle(request);
  }
}
