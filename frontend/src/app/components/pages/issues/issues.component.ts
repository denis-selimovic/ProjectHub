import { Component, OnInit, Input } from '@angular/core';
import { Issue } from 'src/app/models/Issue';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateIssueModalComponent } from '../../modals/create-issue-modal/create-issue-modal.component';
@Component({
  selector: 'app-issues',
  templateUrl: './issues.component.html',
  styleUrls: ['./issues.component.scss']
})
export class IssuesComponent implements OnInit {
  issues: Array<Issue>;

  constructor(private modalService: NgbModal) {
   }

  ngOnInit(): void {
    this.issues = [
      {
        id: "e7165476-b729-11eb-8529-0242ac130003", 
        name: "First issue",
        description: "First description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "CRITICAL"
      },
      {
        id: "e8265476-b729-11eb-8529-0242ac130003", 
        name: "Second issue",
        description: "Second description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "HIGH"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Third issue",
        description: "Third description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "MEDIUM"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Fourth issue",
        description: "Fourth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "LOW"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003", 
        name: "Fifth issue",
        description: "Fifth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "CRITICAL"
      },
      {
        id: "e8265476-b729-11eb-8529-0242ac130003", 
        name: "Sixth issue",
        description: "Sixth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "HIGH"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Seventh issue",
        description: "Seventh description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "MEDIUM"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Eighth issue",
        description: "Eighth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "LOW"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003", 
        name: "Ninth issue",
        description: "Ninth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "CRITICAL"
      },
      {
        id: "e8265476-b729-11eb-8529-0242ac130003", 
        name: "Tenth issue",
        description: "Tenth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "HIGH"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Eleventh issue",
        description: "Eleventh description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "MEDIUM"
      },
      {
        id: "e9365476-b729-11eb-8529-0242ac130003", 
        name: "Twelfth issue",
        description: "Twelfth description",
        projectId: "e7165476-b729-11eb-8529-0242ac130003",
        priority: "LOW"
      }
    ]
  }
  openModal() {
    const modalRef = this.modalService.open(CreateIssueModalComponent);
  }
}
