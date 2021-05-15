import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from '../cookie/cookie.service';

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private user: User | null = null;
  private refreshTokenTimeout: any;

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  login(email: string, password: string): any {
    return this.http.post(
      `${environment.api}/oauth/token`,
      { email, password, grant_type: 'password' },
      {
        headers: new HttpHeaders({
          Authorization:
            'Basic ' + btoa(`${environment.clientId}:${environment.secret}`),
        }),
      }
    );
  }

  logout(): void {
    this.cookieService.deleteCookie('accessToken');
    this.cookieService.deleteCookie('refreshToken');
    this.stopRefreshTokenTimer();
  }

  getAccessToken(): string {
    let token = this.cookieService.getCookie('accessToken');
    if (token === '') {
      return '';
    }
    return 'Bearer ' + token;
  }

  getRefreshToken(): string {
    return this.cookieService.getCookie('refreshToken') || '';
  }

  isLoggedIn(): boolean {
    return this.cookieService.getCookie('accessToken') !== '';
  }

  setToken(
    accessToken: string,
    refreshToken: string,
    expiresInSec: number
  ): void {
    this.cookieService.setCookie('accessToken', accessToken, expiresInSec);
    this.cookieService.setCookie('refreshToken', refreshToken, 900);
    this.startRefreshTokenTimer(expiresInSec);
  }

  refreshToken() {
    //send request to refresh token
  }

  private startRefreshTokenTimer(expiresInSec: number) {
    // const expires = new Date(expiresInSec * 1000);
    // const timeout = expires.getTime() - Date.now() - (60 * 1000);
    // this.refreshTokenTimeout = setTimeout(() => this.refreshToken().subscribe(), timeout);
  }

  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }
}
