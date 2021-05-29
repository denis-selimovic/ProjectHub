import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
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
  @Input() deleteCommentLoader: boolean = false;
  @Input() editCommentLoader: boolean = false;
  @Output() public onDelete: EventEmitter<any> = new EventEmitter();
  @Output() public onPatch: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  deleteComment(comment: any) {
    this.onDelete.emit(comment);
  }

  editComment(patch: any) {
    this.onPatch.emit(patch);
  }

}
