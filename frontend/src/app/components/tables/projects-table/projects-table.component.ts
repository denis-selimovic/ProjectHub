import { AfterViewInit, Component, Input, OnInit, ViewChild, EventEmitter} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs/internal/Observable';
import { Project } from 'src/app/models/Project';

@Component({
  selector: 'app-projects-table',
  templateUrl: './projects-table.component.html',
  styleUrls: ['./projects-table.component.scss']
})
export class ProjectsTableComponent implements AfterViewInit {
  @Input() projects: Array<Project>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  dataSource: MatTableDataSource<Project>;
  obs: Observable<any>;

  constructor() {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSource.paginator = this.paginator;
    });
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<Project>(this.projects);
    this.obs = this.dataSource.connect();
  }

  ngOnDestroy(): void {
    if (this.dataSource) 
      this.dataSource.disconnect(); 
  }

  removeProjectFromTable(id: String): void {
    this.dataSource.data = this.dataSource.data.filter(i => i.id !== id);
  }

}
