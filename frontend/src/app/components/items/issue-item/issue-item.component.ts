import { Component, Input, OnInit, Output } from '@angular/core';
import { Issue } from 'src/app/models/Issue';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateTaskModalComponent } from '../../modals/create-task-modal/create-task-modal.component';
import { EventEmitter } from '@angular/core'; 

@Component({
  selector: 'app-issue-item',
  templateUrl: './issue-item.component.html',
  styleUrls: ['./issue-item.component.scss']
})
export class IssueItemComponent implements OnInit {
  @Input() issue: Issue;
  imageSrc: String;
  @Output() itemEvent = new EventEmitter<Issue>();

  constructor(private modalService: NgbModal) {
   }

  ngOnInit(): void {
    switch (this.issue.priority) {
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



}
