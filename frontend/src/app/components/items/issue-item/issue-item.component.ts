import { Component, Input, OnInit, Output } from '@angular/core';
import { Issue } from 'src/app/models/Issue';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateTaskModalComponent } from '../../modals/create-task-modal/create-task-modal.component';
import { EventEmitter } from '@angular/core'; 
import { IssueService } from 'src/app/services/issue/issue.service';

@Component({
  selector: 'app-issue-item',
  templateUrl: './issue-item.component.html',
  styleUrls: ['./issue-item.component.scss']
})
export class IssueItemComponent implements OnInit {
  @Input() issue: Issue;
  imageSrc: string;
  @Output() itemEvent = new EventEmitter<Issue>();
  @Output() deleteIssueEvent = new EventEmitter<string>();

  constructor(private modalService: NgbModal) {
   }

  ngOnInit(): void {
    console.log("prioritet ovog issue-a je: ", this.issue.priority);
    switch (this.issue.priority.priority_type) {
      case "CRITICAL":
        this.imageSrc = "assets/critical.png";
        break;
      case "HIGH":
        this.imageSrc = "assets/high.png";
        break;
      case "MEDIUM":
        this.imageSrc = "assets/medium.png";
        break;
      case "LOW":
        this.imageSrc = "assets/low.png";
        break;
      default:
        this.imageSrc = "assets/undefined.png";
        break;
    }
  }

  openModal() {
    const modalRef = this.modalService.open(CreateTaskModalComponent);
  }

  detailsClicked(issue: Issue) {
    this.itemEvent.emit(issue);
  }

  removeIssue(id: string) {
    this.deleteIssueEvent.emit(id);
  }
}
