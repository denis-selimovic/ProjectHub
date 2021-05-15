import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

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
    private loggedIn : boolean = false;

    constructor(private http: HttpClient) { }

    login(email: string, password: string): any {
        return this.http.post(`${environment.api}/oauth/token`, { email, password, "grant_type": "password" }, {
            headers: new HttpHeaders({
              Authorization: "Basic " + btoa(`${environment.clientId}:${environment.secret}`)
            })
        });
    }

    getAccessToken(): string {
        if (!this.user) {
          return '';
        }
        return 'Bearer ' + this.user.token.accessToken;
    }

    getRefreshToken(): string {
        return this.user?.token.refreshToken || '';
    }

    isLoggedIn(): boolean {
      return this.loggedIn;
    }

    setLoggedIn(val: boolean): void {
      this.loggedIn = val;
    }
}