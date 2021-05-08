import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, Form } from '@angular/forms';
import { FieldsMatch } from 'src/app/validators/fieldsMatch.validator';

@Component({
  selector: 'app-reset-pass-new-form',
  templateUrl: './reset-pass-new-form.component.html',
  styleUrls: ['./reset-pass-new-form.component.scss']
})

export class ResetPassNewFormComponent implements OnInit {
  resetPassNewForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) {
    this.errorMessage = ""; //error message from server
    this.resetPassNewForm = this.formBuilder.group({
    password: new FormControl('',[Validators.required, 
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}$'),
      Validators.minLength(8)]),
    confirmPassword: new FormControl('', [Validators.required])
  }, {
    validators: FieldsMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void {
  }

}
