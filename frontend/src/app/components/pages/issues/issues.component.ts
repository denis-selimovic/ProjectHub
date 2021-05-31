import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { Issue } from 'src/app/models/Issue';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateIssueModalComponent } from '../../modals/create-issue-modal/create-issue-modal.component';
import { ActivatedRoute } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { IssueService } from 'src/app/services/issue/issue.service';
@Component({
  selector: 'app-issues',
  templateUrl: './issues.component.html',
  styleUrls: ['./issues.component.scss']
})
export class IssuesComponent implements OnInit {
  issues: Array<Issue> = [];
  issue: Issue;
  show: boolean;
  projectId: string;
  title: string;

  issueNum = 0;
  page = 0;
  size = 5;
  pageOptions = [5, 10, 20];
  
  constructor(private modalService: NgbModal, private route: ActivatedRoute, private issueService: IssueService) {
   }

  ngOnInit(): void {
    this.show = false;
    this.title = '';
    this.route.params.subscribe(params => {
      this.projectId = params.id;
    });
    this.loadIssues();
  }

  paginate($event: PageEvent): any {
    if (this.page === $event.pageIndex && this.size === $event.pageSize ) {
      return;
    }
    this.page = $event.pageIndex;
    this.size = $event.pageSize;
    this.loadIssues();
  }

  onIssuesLoad(data: any): any {
    this.issues = data.data;
    this.issueNum = data.metadata.total_elements;
    if (this.issueNum === 0)
      this.title = 'This project doesn\'t have any issues.';
  }

  loadIssues(): any {
    this.issueService.getIssues(this.projectId, this.page, this.size,
      (data: any) => this.onIssuesLoad(data),
      () => this.title = 'Error while loading data. Please try again later.'
    );
  }

  openModal() {
    const modalRef = this.modalService.open(CreateIssueModalComponent);
    modalRef.componentInstance.projectId = this.projectId;
    modalRef.result.then((data) => {
        this.title = '';
        this.loadIssues();
      },
      (error) => {
      });
  }

  detailsClicked(issue: Issue): void {
    this.issue = issue;
    this.show = true;
  }

  closeDetails(value: boolean): void {
    if(value) {
      this.issue = undefined;
      this.show = false;
      this.loadIssues();
    }  
  }

  deleteIssue(): any {
    this.loadIssues();
  }

  issueUpdated(data: any): any {
    this.loadIssues();
  }
}
