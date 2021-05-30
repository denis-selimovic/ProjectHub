import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-project-navbar',
  templateUrl: './project-navbar.component.html',
  styleUrls: ['./project-navbar.component.scss']
})
export class ProjectNavbarComponent implements OnInit {
  @Input() projectId: String = "";

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

}
