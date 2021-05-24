import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Task } from 'src/app/models/Task';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent implements OnInit {

  @Input() tasks: Array<Task> = [];

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  getTaskDetails() {
    this.router.navigate(["/tasks/details"]);
  }

}
