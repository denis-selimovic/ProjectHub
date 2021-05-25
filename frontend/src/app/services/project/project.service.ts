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
      (data: any) => callback(this.getProjectFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  deleteProject(id: string, callback: any, error: any): any {
    this.http.delete(`${environment.api}/api/v1/projects/${id}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }

  getProjectById(id: string, callback: any, error: any): any {
    this.http.get(`${environment.api}/api/v1/projects/${id}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(this.getProjectFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  private getProjectFromResponseData(responseData: any): Project {
    return {id: responseData.id,
      name: responseData.name,
      ownerId: responseData.owner_id,
      createdAt: responseData.created_at,
      updatedAt: responseData.updated_at
    };
  }
}
