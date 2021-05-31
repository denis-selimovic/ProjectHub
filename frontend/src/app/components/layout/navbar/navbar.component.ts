import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent implements OnInit {
  showNotifications = false;
  @Input() hasNotifications;
  @Output() readNotifications: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(public userService: UserService, public router: Router) { }

  ngOnInit(): void { }

  showNotificationsDiv(): any {
    this.showNotifications = !this.showNotifications;
    this.hasNotifications = false;
    this.readNotifications.emit(false);
  }
}
