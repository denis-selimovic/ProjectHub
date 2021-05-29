import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Comment } from 'src/app/services/comment/comment.service';
import { ConfirmDeletionComponent } from '../../dialogs/confirm-deletion/confirm-deletion.component';

@Component({
  selector: 'app-comments-item',
  templateUrl: './comments-item.component.html',
  styleUrls: ['./comments-item.component.scss']
})
export class CommentsItemComponent implements OnInit {
  @Input() comment: Comment;
  @Input() currentUser: any;
  @Input() deleteCommentLoader: boolean = false;
  @Input() editCommentLoader: boolean = false;
  @Output() onPatch: EventEmitter<any> = new EventEmitter<any>();
  @Output() public onDelete: EventEmitter<any> = new EventEmitter();
  
  commentForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      text: new FormControl(this.comment.text, [Validators.required, Validators.maxLength(255)])
    });  
  }

  patchComment() {
    const patch = {comment: this.comment, newCommentText: this.commentForm.get("text").value}
    this.onPatch.emit(patch);
  }

  deleteComment() {
    let dialogRef = this.dialog.open(ConfirmDeletionComponent, {});
    let instance = dialogRef.componentInstance;
    instance.message = "Are you sure you want to delete this comment?";
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.onDelete.emit(this.comment);
      }
    });
  }

  getFormatedDate():string {
    const date = new Date(this.comment.createdAt);
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleTimeString("en-US", options);
  }

}
