import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Project } from '../project/project.service';
import { TokenService } from '../token/token.service';
import { User } from '../user/user.service';

export interface Collaborator {
  id: string;
  project: Project;
  collaboratorId: string;
}

@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getCollaborators(projectId: string, callback: any, error: any): any {
    this.http.get(`${environment.api}/api/v1/projects/${projectId}/collaborators`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      } 
    }).subscribe(
      (data: any) => callback(this.getCollaboratorsFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  getCollaboratorById(projectId: string, id: string, callback: any, error:any): any {
    this.http.get(`${environment.api}/api/v1/projects/${projectId}/collaborators/${id}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      } 
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }

  createCollaborator(projectId: string, form: any, callback: any, error: any): any {
    this.http.post(`${environment.api}/api/v1/projects/${projectId}/collaborators`, form, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }

  deleteCollaborator(projectId: string, id: string, callback: any, error: any): any {
    this.http.delete(`${environment.api}/api/v1/projects/${projectId}/collaborators/${id}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(data),
      (err: any) => error(err)
    );
  }

  private getCollaboratorsFromResponseData(responseData: any): Array<User> {
    let response = new Array<User>();
    for (let i = 0; i < responseData.length; i++) {
      response.push(this.getUserFromResponseData(responseData[i].collaborator));
    }
    return response;
  }

    private getUserFromResponseData(responseData: any): User {
    return {id: responseData.id,
      firstName: responseData.first_name, 
      lastName: responseData.last_name,
      email: responseData.email
    };
  }
}
