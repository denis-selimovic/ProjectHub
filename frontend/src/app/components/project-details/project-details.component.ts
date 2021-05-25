import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit {
  @Input() project: any;
  @Input() owner: User;
  @Input() collaborators: Array<User>;
  @Input() isOwner: boolean;

  constructor() { 
    this.isOwner = true;
  }

  ngOnInit(): void {
  }

}
