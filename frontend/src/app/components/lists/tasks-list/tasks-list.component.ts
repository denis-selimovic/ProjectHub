import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Task } from 'src/app/services/task/task.service';

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

  getTaskDetails(projectId: string, taskId: string) {
    const path = "/projects/" + projectId + "/tasks/" + taskId;
    this.router.navigate([path]);
  }

}
