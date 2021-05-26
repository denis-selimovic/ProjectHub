import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Issue } from 'src/app/models/Issue';

@Component({
  selector: 'app-issues-table',
  templateUrl: './issues-table.component.html',
  styleUrls: ['./issues-table.component.scss']
})
export class IssuesTableComponent implements OnInit {
  
  @Input() issues: any = [];
  @Output() issueEvent = new EventEmitter<Issue>();

  constructor() {
   }

  ngOnInit(): void { }

  detailsClicked(issue: Issue): void {
    this.issueEvent.emit(issue);
  }
  
}
