import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  constructor (private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    if(this.userService.isLoggedIn()) {
      this.router.navigate(["/dashboard"]);
    }
  }
}
