import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { FieldsMatch } from 'src/app/validators/fieldsMatch.validator';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {
  registerForm: FormGroup;
  message = '';
  loader = false;
  redirect = false;

  constructor(private formBuilder: FormBuilder, private userService: UserService) {
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
      validator: FieldsMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void {
    this.registerForm.valueChanges.subscribe(values => {
      this.message = '';
      this.redirect = false;
    });
  }

  onSubmit(): void {
    const form = this.getFormValue();
    this.registerForm.reset();
    this.loader = true;
    setTimeout(() => this.register(form), 2000);
  }

  private register(form: any): any {
    this.userService.register(form,
      (data: any) => this.success(), (error: any) => this.failure());
  }

  private getFormValue(): any {
    return {
      email: this.registerForm.get('email')?.value,
      password: this.registerForm.get('password')?.value,
      confirm_password: this.registerForm.get('confirmPassword')?.value,
      first_name: this.registerForm.get('firstName')?.value,
      last_name: this.registerForm.get('lastName')?.value
    };
  }

  success(): any {
    this.loader = false;
    this.redirect = true;
  }

  failure(): any {
    this.loader = false;
    this.message = 'Something went wrong. Please try again later.';
  }
}
