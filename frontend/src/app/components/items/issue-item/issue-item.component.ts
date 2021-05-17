import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-issue-item',
  templateUrl: './issue-item.component.html',
  styleUrls: ['./issue-item.component.scss']
})
export class IssueItemComponent implements OnInit {
  issue: any;

  constructor() {
    this.issue = {
      name: "First issue",
      description: "This is description",
      priority: "HIGH"
    }
   }

  ngOnInit(): void {
  }

}
