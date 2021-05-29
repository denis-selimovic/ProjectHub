import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Comment, CommentService } from 'src/app/services/comment/comment.service';
import { User } from 'src/app/services/user/user.service';
import { ModalComponent } from '../../modal/modal/modal.component';

@Component({
  selector: 'app-comments-list',
  templateUrl: './comments-list.component.html',
  styleUrls: ['./comments-list.component.scss']
})
export class CommentsListComponent implements OnInit {
  @Input() taskId: String = null;
  @Input() comments: Array<Comment> = [];
  @Input() currentUser: User;
  @Input() deleteCommentLoader: boolean = false;
  @Input() editCommentLoader: boolean = false;
  @Input() loadMoreAvailable: boolean = true;
  @Output() public onDelete: EventEmitter<any> = new EventEmitter();
  @Output() public onPatch: EventEmitter<any> = new EventEmitter();
  @Output() public onPaginate: EventEmitter<any> = new EventEmitter();

  constructor(private commentService: CommentService, private modal: NgbModal) { }

  ngOnInit(): void {
  }

  deleteComment(comment: any) {
    const deleteModal = this.modal.open(ModalComponent);
    deleteModal.componentInstance.message = 'Are you sure you want to delete this comment?';
    deleteModal.componentInstance.successMessage = '';
    deleteModal.componentInstance.errorMessage = '';
    deleteModal.componentInstance.action = () => {
      this.commentService.deteteComment(this.taskId ,comment.id, (data: any) => {
        deleteModal.componentInstance.successMessage = 'Comment successfully deleted.';
      }, (err: any) => {
        deleteModal.componentInstance.errorMessage = 'Something went wrong when deleting the comment. Please try again.';
      });
    };
    deleteModal.result.then(result => {
      if (result === 'success') {
        this.onDelete.emit();
      }
    }).catch(err => {});
  }

  editComment(patch: any) {
    this.onPatch.emit(patch);
  }

  paginate() {
    this.onPaginate.emit();
  }

}
