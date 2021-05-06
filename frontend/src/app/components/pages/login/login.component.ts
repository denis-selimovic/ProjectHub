import { Component } from '@angular/core';
import { FormControl, FormBuilder , Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  isSubmitted = false;

  loginForm = this.fb.group({
    email: ["", Validators.required],
    password: ["", Validators.required]
  });

  constructor(private fb: FormBuilder) { }

  get formControls() { return this.loginForm.controls; }

  onSubmit() {
    this.isSubmitted = true;
    console.log(this.loginForm.value);
  }
}
