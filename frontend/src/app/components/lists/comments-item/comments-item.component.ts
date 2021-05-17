import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDeletionComponent } from '../../dialogs/confirm-deletion/confirm-deletion.component';

@Component({
  selector: 'app-comments-item',
  templateUrl: './comments-item.component.html',
  styleUrls: ['./comments-item.component.scss']
})
export class CommentsItemComponent implements OnInit {
  @Output() public onDelete: EventEmitter<any> = new EventEmitter();
  @Input() comment: any;
  @Input() currentUser: any;
  commentForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      comment: new FormControl(this.comment.text, [Validators.required, Validators.maxLength(255)])
    });  
  }

  patchComment(comment: String) {
    console.log(comment);
  }

  deleteComment() {
    this.onDelete.emit();
    // let dialogRef = this.dialog.open(ConfirmDeletionComponent);
    // let instance = dialogRef.componentInstance;
    // instance.message = "Are you sure you want to delete this comment?";
    // dialogRef.afterClosed().subscribe(result => {
    //   if (result) {
    //     this.onDelete.emit();
    //   }
    // });
  }

}
