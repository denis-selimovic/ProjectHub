import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from '../cookie/cookie.service';
import { ActivatedRoute, Router } from '@angular/router';

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

  constructor(private http: HttpClient, private cookieService: CookieService, private route: ActivatedRoute, private router: Router) {}

  login(email: string, password: string, errorHandler: any): any {
    this.http.post(`${environment.api}/oauth/token`,{ email, password, grant_type: 'password' },{
        headers: new HttpHeaders({
          Authorization:
            'Basic ' + btoa(`${environment.clientId}:${environment.secret}`),
        }),
      }
    ).subscribe((body: any) => {
      // set user data (email)
      this.setToken(body.data.access_token, body.data.refresh_token, body.data.expires_in);
      this.route.queryParams.subscribe((queryParams) => {
        if (queryParams.return) {
          this.router.navigate([queryParams.return]);
        } else {
          this.router.navigate(['dashboard']);
        }
      });
    },(error: any) => {errorHandler(error);});
  }

  logout(): void {
    this.cookieService.deleteCookie('accessToken');
    this.cookieService.deleteCookie('refreshToken');
    this.router.navigate([""]);
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

  setToken(accessToken: string, refreshToken: string, expiresInSec: number): void {
    this.cookieService.setCookie('accessToken', accessToken, expiresInSec);
    this.cookieService.setCookie('refreshToken', refreshToken, 900);
  }
}
