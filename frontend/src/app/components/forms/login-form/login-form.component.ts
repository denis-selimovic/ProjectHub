import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required,Validators.email],
      password: ['', Validators.required]
    });
    this.errorMessage = ""; //error message from server
  }

  ngOnInit(): void {
  }

  onSubmit(): void { }
}
