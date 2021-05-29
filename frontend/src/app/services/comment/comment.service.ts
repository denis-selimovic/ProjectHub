import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TokenService } from '../token/token.service';

export interface Comment {
  id: String;
  text: String;
  userId: String;
  userFirstName: String;
  userLastName: String;
}

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  addComment(taskId: string, commentText: string): void {
    const body = {text: commentText};
    this.http.post(`${environment.api}/api/v1/tasks/${taskId}/comment`, body, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => console.log(data),
      (err: any) => console.log(err)
    );
  }

  getComments(taskId: string, successCallback: any, errorCallback: any): void {
    this.http.get(`${environment.api}/api/v1/tasks/${taskId}/comments`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => successCallback(data),
      (err: any) => errorCallback(err)
    );
  }

  deteteComment(commentId: string): void {
    this.http.delete(`${environment.api}/api/v1/comments/${commentId}`, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => console.log(data),
      (err: any) => console.log(err)
    );
  }

  editComment(commentId: string, newCommentText: string): void {
    const body = {text: newCommentText};
    this.http.patch(`${environment.api}/api/v1/comments/${commentId}`, body, {
      headers: {
        Authorization: this.tokenService.getAccessToken()
      }
    }).subscribe(
      (data: any) => console.log(data),
      (err: any) => console.log(err)
    );
  }
}
