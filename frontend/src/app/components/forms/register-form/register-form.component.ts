import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { FieldsMatch } from 'src/app/validators/fieldsMatch.validator';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: String;

  constructor(private formBuilder: FormBuilder) {
    this.errorMessage = ""; //error message from server
    this.registerForm = this.formBuilder.group({
      firstName: new FormControl('', [Validators.required, Validators.maxLength(32)]),
      lastName: new FormControl('', [Validators.required, Validators.maxLength(32)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('',[Validators.required, 
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}$'),
        Validators.minLength(8)]
      ),
      confirmPassword: new FormControl('', [Validators.required])
    },{
      validators: FieldsMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void { }
}
