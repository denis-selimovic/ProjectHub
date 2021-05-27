import { Component, Input, OnInit } from '@angular/core';
import { Task } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-tasks-item',
  templateUrl: './tasks-item.component.html',
  styleUrls: ['./tasks-item.component.scss']
})
export class TasksItemComponent implements OnInit {

  @Input() task: Task;
  imageSrc: String;

  constructor() {
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

}
