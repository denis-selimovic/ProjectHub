import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from '../cookie/cookie.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenService } from '../token/token.service';

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

  constructor(private http: HttpClient,
    private tokenService: TokenService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  login(email: string, password: string, errorHandler: any): any {
    this.http.post(`${environment.api}/oauth/token`,{ email, password, grant_type: 'password' },{
        headers: new HttpHeaders({
          Authorization:
            'Basic ' + btoa(`${environment.clientId}:${environment.secret}`),
        }),
      }
    ).subscribe((body: any) => {
      // set user data (email)
      this.tokenService.setToken(body.data.access_token, body.data.refresh_token, body.data.expires_in);
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
    this.tokenService.removeToken('accessToken');
    this.tokenService.removeToken('refreshToken');
    this.router.navigate([""]);
  }

  isLoggedIn(): boolean {
    return this.tokenService.getAccessToken() !== "";
  }

  register(registerForm: any, success: any, failure: any): any {
    this.http.post(`${environment.api}/api/v1/users`, registerForm).subscribe(
      (data: any) => success(data),
      (error: any) => failure(error)
    );
  }
}
