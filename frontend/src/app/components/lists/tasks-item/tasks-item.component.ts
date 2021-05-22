import { Component, Input, OnInit } from '@angular/core';
import { Task } from 'src/app/models/Task';

@Component({
  selector: 'app-tasks-item',
  templateUrl: './tasks-item.component.html',
  styleUrls: ['./tasks-item.component.scss']
})
export class TasksItemComponent implements OnInit {

  @Input() task: any;
  initials: String;
  imageSrc: String;

  constructor() {
  }

  ngOnInit(): void {
    const firstLastName = this.task.userName.split(' ');
    this.initials = this.task === undefined ? '' :firstLastName[0][0] + firstLastName[1][0];
    switch (this.task.type.type) {
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
