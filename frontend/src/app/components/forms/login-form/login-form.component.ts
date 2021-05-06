import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder , Validators } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  loginForm = this.fb.group({
    email: ["", Validators.required],
    password: ["", Validators.required]
  });

  get formControls() { return this.loginForm.controls; }

  onSubmit() {
    console.log(this.loginForm.value);
  }

}
