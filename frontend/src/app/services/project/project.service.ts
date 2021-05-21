import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TokenService } from '../token/token.service';
import { environment } from 'src/environments/environment';

export interface Project {
  id: string;
  name: string;
  ownerId: string;
  createdAt: Date;
  updatedAt: Date;
}


@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getProjects(filter: string, page: number, size: number, callback: any, error: any): any {
    this.http.get(`${environment.api}/api/v1/projects?filter=${filter}&page=${page}&size=${size}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }

  createProject(form: any, callback: any, error: any): any {
    this.http.post(`${environment.api}/api/v1/projects`, form, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data): any => callback(data),
      (err: any) => error(err)
    );
  }
}
