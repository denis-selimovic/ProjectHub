import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit {
  @Input() project: any
  @Input() owner: User // GET /api/v1/users/{ownerId}
  @Input() collaborators: Array<User> // GET /api/v1/projects/{projectId}/collaborators =>  GET /api/v1/users/{collaboratorId}
  isOwner: boolean

  constructor() { 
    this.isOwner = true;
  }

  ngOnInit(): void {
  }

}
