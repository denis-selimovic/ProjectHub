import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Project, ProjectService } from '../project/project.service';
import { TokenService } from '../token/token.service';
import { User } from '../user/user.service';

export interface Collaborator {
  id: string;
  project: Project;
  collaboratorId: string;
  collaborator: User;
}

@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpClient, private tokenService: TokenService, private projectService: ProjectService) { }

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

  private getCollaboratorsFromResponseData(responseData: any): Array<Collaborator> {
    let response = new Array<Collaborator>();
    for (let i = 0; i < responseData.length; i++) {
      response.push(this.getCollaboratorFromResponseData(responseData[i]));
    }
    return response;
  }

  private getCollaboratorFromResponseData(responseData: any): Collaborator {
    return {id: responseData.id,
      project: this.projectService.getProjectFromResponseData(responseData.project),
      collaboratorId: responseData.collaborator_id,
      collaborator: {
        id: responseData.collaborator.id,
        firstName: responseData.collaborator.first_name, 
        lastName: responseData.collaborator.last_name,
        email: responseData.collaborator.email
      }
    };
  }
}
