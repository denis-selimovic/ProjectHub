import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-reset-pass-email-form',
  templateUrl: './reset-pass-email-form.component.html',
  styleUrls: ['./reset-pass-email-form.component.scss']
})
export class ResetPassEmailFormComponent implements OnInit {
  resetPassEmailForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) {
    this.resetPassEmailForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
    });
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

}
