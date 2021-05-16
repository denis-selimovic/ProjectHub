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
    return `${currentUser.firstName} ${currentUser.lastName.charAt(0)}.`
  }

}
