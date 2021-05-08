import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-reset-pass-code-form',
  templateUrl: './reset-pass-code-form.component.html',
  styleUrls: ['./reset-pass-code-form.component.scss']
})
export class ResetPassCodeFormComponent implements OnInit {
  resetPassCodeForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) {
    this.resetPassCodeForm = this.formBuilder.group({
      code: new FormControl('', [Validators.required]),
    });
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

}
