import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Project } from 'src/app/models/Project';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.scss']
})
export class ProjectItemComponent implements OnInit {
  @Input() project: Project;
  @Output() newItemEvent  = new EventEmitter<String>();

  constructor() { }

  ngOnInit(): void {
  }

  removeProject(value: String): void {
    //todo confirm deletion
    this.newItemEvent.emit(value);
  }

}
