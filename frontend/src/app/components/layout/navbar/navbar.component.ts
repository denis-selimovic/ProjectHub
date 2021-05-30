import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent implements OnInit {
  showNotifications = false;
  @Input() hasNotifications = false;

  constructor(public userService: UserService, public router: Router) { }

  ngOnInit(): void {  }

  showNotificationsDiv(): any {
    this.showNotifications = !this.showNotifications;
    if (this.hasNotifications) { this.hasNotifications = false; }
  }
}
