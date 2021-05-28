import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Task } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-tasks-item',
  templateUrl: './tasks-item.component.html',
  styleUrls: ['./tasks-item.component.scss']
})
export class TasksItemComponent implements OnInit {

  @Input() task: Task;
  imageSrc: String;
  @Output() taskDelete: EventEmitter<string> = new EventEmitter<string>();

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    switch (this.task.priority.priority) {
      case "HIGH":
        this.imageSrc = "assets/priority_high.png";        
        break;
      case "MEDIUM":
        this.imageSrc = "assets/priority_medium.png";        
        break;
      case "LOW":
        this.imageSrc = "assets/priority_low.png";        
        break;
      case "CRITICAL":
        this.imageSrc = "assets/priority_critical.png";        
        break;
    }
  }

  getTaskDetails(projectId: string, taskId: string) {
    const path = "/projects/" + projectId + "/tasks/" + taskId;
    this.router.navigate([path]);
  }

  deleteTask() {
    this.taskDelete.emit(this.task.id);
  }
}
