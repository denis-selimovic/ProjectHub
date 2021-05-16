import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserService } from '../user/user.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor{

  constructor(private userService: UserService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
        if ([401, 403].includes(err.status) && this.userService.isLoggedIn) {
            // auto logout if 401 or 403 response returned from api
            this.userService.logout();
        }

        // const error = (err && err.error && err.error.message) || err.statusText;
        // console.error(err);
        return throwError(err);
    }))
}
}
