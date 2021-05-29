import { Component, Input, OnInit } from '@angular/core';
import { Comment } from 'src/app/services/comment/comment.service';
import { User } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-comments-list',
  templateUrl: './comments-list.component.html',
  styleUrls: ['./comments-list.component.scss']
})
export class CommentsListComponent implements OnInit {
  @Input() comments: Array<Comment> = [];
  @Input() currentUser: User;

  constructor() { }

  ngOnInit(): void {
  }

  deleteComment(comment: any) {
    
  }

}
