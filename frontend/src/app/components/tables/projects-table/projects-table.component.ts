import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-projects-table',
  templateUrl: './projects-table.component.html',
  styleUrls: ['./projects-table.component.scss']
})
export class ProjectsTableComponent implements OnInit {

  @Input() projects: any = [];

  constructor() { }

  ngOnInit(): void { }
}
