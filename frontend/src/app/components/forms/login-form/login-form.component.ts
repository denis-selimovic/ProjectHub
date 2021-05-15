import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: String;
  failedLogin: boolean;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router, private route: ActivatedRoute) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
    this.errorMessage = "";
    this.failedLogin = false;
  }

  ngOnInit(): void {
    this.loginForm.valueChanges.subscribe(() => {
      this.errorMessage = "";
      this.failedLogin = false;
    });
  }

  onSubmit(): void { 
    this.login();
  }

  login(): void {
    this.userService.login(this.loginForm.get('email')?.value, this.loginForm.get('password')?.value)
    .subscribe((body: any) => {
      this.failedLogin = false;
      this.userService.setToken(body.data.access_token, body.data.refresh_token, body.data.expires_in);
      this.route.queryParams.subscribe(queryParams => {
        if (queryParams.return) {
          this.router.navigate([queryParams.return]);
        }
        else {
          this.router.navigate(['dashboard']);
        }
      });
    }, (error: any) => {
      this.failedLogin = true;
      if(error.status === 400 || error.status === 401) {
        this.errorMessage = "Invalid credentials";
      }else {
        this.errorMessage = "Error while logging in. Try again";
      }
    });
  }
}
