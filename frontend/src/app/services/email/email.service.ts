import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(private http: HttpClient) { }

  confirmEmail(token: string, successCallback: any, errorCallback: any): any {
    this.http.post(`${environment.api}/api/v1/users/confirm-email`, { token })
      .subscribe((body: any) => {
        successCallback(body);
      }, (error: any) => {
        errorCallback(error);
      });
  }

  requestPasswordReset(form: any, callback: any, error: any): any {
    this.http.post(`${environment.api}/api/v1/users/request-password-reset`, form)
      .subscribe(
        (data: any) => callback(data),
        (err: any) => error(err)
      );
  }
}
