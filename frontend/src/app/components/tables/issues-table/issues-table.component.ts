import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal/modal.component';
import { Issue } from 'src/app/models/Issue';
import { IssueService } from 'src/app/services/issue/issue.service';

@Component({
  selector: 'app-issues-table',
  templateUrl: './issues-table.component.html',
  styleUrls: ['./issues-table.component.scss']
})
export class IssuesTableComponent implements OnInit {
  
  @Input() issues: any = [];
  @Output() issueEvent = new EventEmitter<Issue>();
  @Output() issueDeleteEvent = new EventEmitter<any>();

  constructor(private modal: NgbModal, private issueService: IssueService) {
   }

  ngOnInit(): void { }

  detailsClicked(issue: Issue): void {
    this.issueEvent.emit(issue);
  }

  deleteIssue(issueId: string) {
    const deleteModal = this.modal.open(ModalComponent);
    deleteModal.componentInstance.message = 'Are you sure you want to delete this issue?';
    deleteModal.componentInstance.successMessage = '';
    deleteModal.componentInstance.errorMessage = '';
    deleteModal.componentInstance.action = () => {
      this.issueService.deleteIssue(issueId, (data: any) => {
        deleteModal.componentInstance.successMessage = 'Issue successfully deleted.';
      }, (err: any) => {
        deleteModal.componentInstance.errorMessage = 'Something went wrong when deleting issue. Please try again.';
      });
    };
    deleteModal.result.then(result => {
      if (result === 'success') {
        this.issueDeleteEvent.emit();
      }
    }).catch(err => {});
  }
}
