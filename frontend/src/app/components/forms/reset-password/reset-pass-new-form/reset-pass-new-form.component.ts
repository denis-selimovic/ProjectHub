import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { FieldsMatch } from 'src/app/validators/fieldsMatch.validator';

@Component({
  selector: 'app-reset-pass-new-form',
  templateUrl: './reset-pass-new-form.component.html',
  styleUrls: ['./reset-pass-new-form.component.scss']
})

export class ResetPassNewFormComponent implements OnInit {
  resetPassNewForm: FormGroup;
  hide = true;
  hideConf = true;
  loader = false;

  @Input() message = '';
  @Output() formSubmit: EventEmitter<any> = new EventEmitter<any>();

  constructor(private formBuilder: FormBuilder) {
    this.resetPassNewForm = this.formBuilder.group({
    password: new FormControl('', [Validators.required,
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}$'),
      Validators.minLength(8)]),
    confirmPassword: new FormControl('', [Validators.required])
  }, {
    validators: FieldsMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void { }

  submitForm(): any {
    this.loader = true;
    const form = this.getFormValue();
    this.resetPassNewForm.reset();
    setTimeout(() => this.emitPasswordReset(form), 1200);
  }

  private emitPasswordReset(form: any): any {
    this.loader = false;
    this.formSubmit.emit(form);
  }

  private getFormValue(): any {
    return {
      password: this.resetPassNewForm.get('password').value,
      confirm_password: this.resetPassNewForm.get('confirmPassword').value
    };
  }
}

