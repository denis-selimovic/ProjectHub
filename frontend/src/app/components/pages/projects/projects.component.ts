import { Component, OnInit } from '@angular/core';
import {MatPaginatorModule} from '@angular/material/paginator';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  projects: Array<Project>;

  constructor() {
   }

  ngOnInit(): void {
  }

}
