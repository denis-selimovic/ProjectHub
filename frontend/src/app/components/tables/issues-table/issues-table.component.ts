import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { Issue } from 'src/app/models/Issue';

@Component({
  selector: 'app-issues-table',
  templateUrl: './issues-table.component.html',
  styleUrls: ['./issues-table.component.scss']
})
export class IssuesTableComponent implements AfterViewInit {
  @Input() issues: Array<Issue>;

  constructor() {

   }

   ngAfterViewInit(): void {
  }

  ngOnInit(): void {
  }

}
