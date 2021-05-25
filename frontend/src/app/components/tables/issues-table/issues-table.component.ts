import { AfterViewInit, Component, Input, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Issue } from 'src/app/models/Issue';
import { Observable } from 'rxjs/internal/Observable';
import { IssueDetailsFormComponent } from 'src/app/components/forms/issue-details-form/issue-details-form.component';

@Component({
  selector: 'app-issues-table',
  templateUrl: './issues-table.component.html',
  styleUrls: ['./issues-table.component.scss']
})
export class IssuesTableComponent implements AfterViewInit {
  @Input() issues: Array<Issue>;
  @Output() issueEvent = new EventEmitter<Issue>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  dataSource: MatTableDataSource<Issue>;
  obs: Observable<any>;

  constructor() {
   }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSource.paginator = this.paginator;
    });
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<Issue>(this.issues);
    this.obs = this.dataSource.connect();
  }

  ngOnDestroy(): void {
    if (this.dataSource) 
      this.dataSource.disconnect(); 
  }

  detailsClicked(issue: Issue): void {
    this.issueEvent.emit(issue);
  }
  
}
