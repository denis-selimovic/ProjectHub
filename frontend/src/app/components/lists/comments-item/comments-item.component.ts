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
  @Output() patch: EventEmitter<any> = new EventEmitter<any>();
  @Output() public onDelete: EventEmitter<any> = new EventEmitter();
  
  commentForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      text: new FormControl(this.comment.text, [Validators.required, Validators.maxLength(255)])
    });  
  }

  patchComment(text: String) {
    this.patch.emit(this.commentForm);
  }

  deleteComment() {
    let dialogRef = this.dialog.open(ConfirmDeletionComponent, {});
    let instance = dialogRef.componentInstance;
    instance.message = "Are you sure you want to delete this comment?";
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.onDelete.emit();
      }
    });
  }

}
