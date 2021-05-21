import { Component, Input, OnInit } from '@angular/core';
import { Issue } from 'src/app/models/Issue';

@Component({
  selector: 'app-issue-item',
  templateUrl: './issue-item.component.html',
  styleUrls: ['./issue-item.component.scss']
})
export class IssueItemComponent implements OnInit {
  @Input() issue: Issue;
  imageSrc: String;

  constructor() {
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

}
