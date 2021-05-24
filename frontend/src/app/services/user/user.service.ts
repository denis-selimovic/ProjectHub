import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenService } from '../token/token.service';
import { LocalStorageService } from '../local-storage/local-storage.service';

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
    private router: Router,
    private storageService: LocalStorageService
  ) {}

  login(email: string, password: string, errorHandler: any): any {
    this.http.post(`${environment.api}/oauth/token`,{ email, password, grant_type: 'password' },{
        headers: new HttpHeaders({
          Authorization:
            'Basic ' + btoa(`${environment.clientId}:${environment.secret}`),
        }),
      }
    ).subscribe((body: any) => {
      this.tokenService.setToken(body.data.access_token, body.data.refresh_token, body.data.expires_in);
      this.fetchUserDetails(() => {
        this.route.queryParams.subscribe((queryParams) => {
          if (queryParams.return) {
            this.router.navigate([queryParams.return]);
          } else {
            this.router.navigate(['dashboard']);
          }
        });
      });
    },(error: any) => {errorHandler(error);});
  }

  logout(): void {
    this.tokenService.removeToken('accessToken');
    this.tokenService.removeToken('refreshToken');
    this.storageService.clearStorage();
    this.router.navigate(["/login"]);
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

  getCurrentUser(): User {
    return this.storageService.retrieveObject("user");
  }

  changePassword(form: any, success: any, failure: any): void {
    this.http.post(`${environment.api}/api/v1/users/change-password`, form, {
      headers: new HttpHeaders({
        Authorization: this.tokenService.getAccessToken()
      }),
    }).subscribe(
      (data: any) => success(data),
      (error: any) => failure(error)
    );
  }

  private fetchUserDetails(successHandler: any): void {
    this.http.get(`${environment.api}/api/v1/users/user-details`,{
      headers: new HttpHeaders({
        Authorization: this.tokenService.getAccessToken()
      }),
    }).subscribe((response: any) => {
      this.user = this.getUserFromResponseData(response.data);
      this.storageService.saveObject(this.user, "user");
      successHandler();
    });
  }

  private getUserFromResponseData(responseData: any): User {
    return {id: responseData.id,
      firstName: responseData.first_name, 
      lastName: responseData.last_name,
      email: responseData.email
    };
  }
}
