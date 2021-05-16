import { Injectable } from '@angular/core';
import { CookieService } from '../cookie/cookie.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private cookieService: CookieService) {}

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

  setToken(accessToken: string, refreshToken: string, expiresInSec: number): void {
    this.cookieService.setCookie('accessToken', accessToken, expiresInSec);
    this.cookieService.setCookie('refreshToken', refreshToken, 900);
  }

  removeToken(tname: string): void {
    this.cookieService.deleteCookie(tname);
  }
}
