import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import {TokenService} from '../token/token.service';

export interface Notification {
  id: string;
  title: string;
  description: string;
  created_at: Date;
  updated_at: Date;
  read: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getNotifications(page, size, callback, error): any {
    this.http.get(`${environment.api}/api/v1/notifications?page=${page}&size=${size}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }
}
