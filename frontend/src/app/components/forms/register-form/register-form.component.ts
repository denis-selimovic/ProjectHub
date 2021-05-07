import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: String
  validPasswordPattern: RegExp

  constructor(private formBuilder: FormBuilder) {
    this.validPasswordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}/;
    this.errorMessage = ""; //error message from server
    this.registerForm = this.formBuilder.group({
      firstName: new FormControl('', [Validators.required, Validators.maxLength(32)]),
      lastName: new FormControl('', [Validators.required, Validators.maxLength(32)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('',[ Validators.required, Validators.pattern(this.validPasswordPattern)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.pattern(this.validPasswordPattern)])
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void { }
}
