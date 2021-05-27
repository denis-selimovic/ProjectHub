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
  status: string;
}

export interface Task {
  id: string;
  name: string;
  description: string;
  userId: string;
  userFirstName: string,
  userLastName: string,
  projectId: string,
  projectName: string,
  priority: Priority,
  status: Status,
  type: Type,
  createdAt: Date,
  updatedAt: Date,
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

  getTasksByProjectId(projectId: string, callback: any, error: any): any {
    this.http.get(`${environment.api}/api/v1/tasks?project_id=${projectId}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(this.getTasksFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  getTaskById(id: string, callback: any, error: any): any {
    this.http.get(`${environment.api}/api/v1/tasks/${id}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(this.getTaskFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  createTask(form: any, callback: any, error: any): any {
    this.http.post(`${environment.api}/api/v1/tasks`, form, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(this.getTaskFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  patchTaskDescription(taskId: string, description: string, callback: any, error: any): any {
    this.http.patch(`${environment.api}/api/v1/tasks/${taskId}`, 
      {
        description: description
      }, 
      {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => callback(this.getTaskFromResponseData(data.data)),
      (err: any) => error(err)
    );
  }

  private getTasksFromResponseData(responseData: any): Array<Task> {
    let tasks = new Array<Task>();
    for (let i = 0; i < responseData.length; i++) {
      tasks.push(this.getTaskFromResponseData(responseData[i]));
    }
    return tasks;
  }

  private getTaskFromResponseData(responseData: any): Task {
    return {id: responseData.id,
      name: responseData.name,
      description: responseData.description,
      userId: responseData.user_id,
      userFirstName: responseData.user_first_name,
      userLastName: responseData.user_last_name,
      projectId: responseData.project_id,
      projectName: responseData.project_name,
      priority: responseData.priority,
      status: responseData.status,
      type: responseData.type,
      createdAt: responseData.created_at,
      updatedAt: responseData.updated_at,
    };
  }
}
