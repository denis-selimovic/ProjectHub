import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { Collaborator } from 'src/app/services/collaborator/collaborator.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit {
  @Input() project: any;
  @Input() owner: User;
  @Input() collaborators: Array<Collaborator>;
  @Input() isOwner: boolean;

  constructor() { 
    this.isOwner = true;
  }

  ngOnInit(): void {
  }

}
