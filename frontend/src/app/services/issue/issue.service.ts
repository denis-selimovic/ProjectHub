import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Project } from '../project/project.service';
import { TokenService } from '../token/token.service';
import { User } from '../user/user.service';

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
        console.log("dosao da trazim issues ", projectId);
        this.http.get(`${environment.api}/api/v1/issues?project_id=${projectId}&page=${page}&size=${size}`, {
            headers: {
                Authorization: this.tokenService.getAccessToken()
            }
        }).subscribe(
            (data: any) => callback(data),
            (err: any) => error(err)
        );
    }

}