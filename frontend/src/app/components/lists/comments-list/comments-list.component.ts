import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-comments-list',
  templateUrl: './comments-list.component.html',
  styleUrls: ['./comments-list.component.scss']
})
export class CommentsListComponent implements OnInit {
  @Input() comments: Array<any>;
  @Input() currentUser: any;

  constructor() { }

  ngOnInit(): void {
  }

  deleteComment(comment: any) {
    this.comments = this.comments.filter(el => el !== comment);
  }

}
