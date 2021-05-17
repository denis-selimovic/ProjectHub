import { Component, OnInit, Input } from '@angular/core';
import { Issue } from 'src/app/models/Issue';

@Component({
  selector: 'app-issues',
  templateUrl: './issues.component.html',
  styleUrls: ['./issues.component.scss']
})
export class IssuesComponent implements OnInit {
  issues: Array<Issue>;

  constructor() {
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
      }
    ]
  }
}
