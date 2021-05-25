import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TokenService } from '../token/token.service';

export interface Priority {
  id: string;
  priority: string;
}

export interface Type {
  id: string;
  type: string;
}

export interface Status {
  id: string;
  type: string;
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getPriorities(callback: any): any {
    this.http.get(`${environment.api}/api/v1/priorities`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data)
    );
  }

  getTypes(callback: any): any {
    this.http.get(`${environment.api}/api/v1/types`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data)
    );
  }

  getStatuses(callback: any): any {
    this.http.get(`${environment.api}/api/v1/statuses`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data)
    );
  }
}
