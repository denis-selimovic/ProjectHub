import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {
  title: string = "Change password";
  links: boolean = false;
  error: string = "";
  message: string = "";

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  changePassword = (form: any) => {
    this.userService.changePassword(form, 
      (response) => {this.message = "Password successfully changed!"},
      (error) => {this.error = "Something went wrong, try again later."});
  }

}
