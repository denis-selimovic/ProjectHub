import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-projects-table',
  templateUrl: './projects-table.component.html',
  styleUrls: ['./projects-table.component.scss']
})
export class ProjectsTableComponent implements OnInit {
  project: any;

  constructor() {
    this.project = {
      name: 'WT PROJECT'
    }
   }

  ngOnInit(): void {
  }

}
