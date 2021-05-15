import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from '../cookie/cookie.service';

export interface Token {
    accessToken: string;
    refreshToken: string;
    expiresIn: number;
}

export interface User {
    id: any;
    firstName: string;
    lastName: string;
    email: string;
    token: Token;
}

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private user: User | null = null;

    constructor(private http: HttpClient, private cookieService: CookieService) { }

    login(email: string, password: string): any {
        return this.http.post(`${environment.api}/oauth/token`, { email, password, "grant_type": "password" }, {
            headers: new HttpHeaders({
              Authorization: "Basic " + btoa(`${environment.clientId}:${environment.secret}`)
            })
        });
    }

    getAccessToken(): string {
      let token = this.cookieService.getCookie("accessToken");
      if (token === "") {
        return '';
      }
      return 'Bearer ' + token;
    }

    getRefreshToken(): string {
      return this.user?.token.refreshToken || '';
    }

    isLoggedIn(): boolean {
      return this.cookieService.getCookie("accessToken") !== "";
    }

    setToken(accessToken: string, refreshToken: string, expiresInSec: number): void {
      this.cookieService.setCookie("accessToken", accessToken, expiresInSec);
    }
}