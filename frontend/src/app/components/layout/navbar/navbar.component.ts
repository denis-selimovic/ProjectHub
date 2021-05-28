import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent implements OnInit {
  showNotifications: boolean = false;
  hasNotifications: boolean = false;

  constructor(public userService: UserService, public router: Router) {
    
   }

  ngOnInit(): void {
    //just for testing
    setTimeout(() => {
      this.hasNotifications = true;
    }, 5000);
  }

  showNotificationsDiv() {
    this.showNotifications = !this.showNotifications;
    if(this.hasNotifications) this.hasNotifications = false;
  }
}
