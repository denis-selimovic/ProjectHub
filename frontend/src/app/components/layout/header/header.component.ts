import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  fullname(): string {
    const currentUser = this.userService.getCurrentUser();
    if(!currentUser) return "";
    return `${currentUser.firstName} ${currentUser.lastName.charAt(0)}.`
  }

  isUserLoggedIn(): boolean {
    return this.userService.isLoggedIn();
  }

  logout(): void {
    this.userService.logout();
  }
}
