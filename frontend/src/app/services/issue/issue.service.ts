import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Project } from '../project/project.service';
import { TokenService } from '../token/token.service';

export interface Issue {
    id: string,
    project: Project,
    issueId: string
}
@Injectable({
    providedIn: 'root'
})
export class IssueService {

    constructor(private http: HttpClient, private tokenService: TokenService) { }

    getIssues(projectId: string, page: number, size: number, callback: any, error: any): any {
        this.http.get(`${environment.api}/api/v1/issues?project_id=${projectId}&page=${page}&size=${size}`, {
            headers: {
                Authorization: this.tokenService.getAccessToken()
            }
        }).subscribe(
            (data: any) => callback(data),
            (err: any) => error(err)
        );
    }

    createIssue(form: any, callback: any, error: any): any {
        this.http.post(`${environment.api}/api/v1/issues`, form, {
          headers: {
            Authorization: this.tokenService.getAccessToken()
          }
        }).subscribe(
          (data: any) => callback(data),
          (err: any) => error(err)
        );
      }

    deleteIssue(id: string, callback: any, error: any): any {
        this.http.delete(`${environment.api}/api/v1/issues/${id}`, {
          headers: {
            Authorization: this.tokenService.getAccessToken()
          }
        }).subscribe(
          (data: any) => callback(data),
          (err: any) => error(err)
        );
    }
}