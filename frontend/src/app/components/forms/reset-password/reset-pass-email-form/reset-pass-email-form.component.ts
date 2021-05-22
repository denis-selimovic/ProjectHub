import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { EmailService } from '../../../../services/email/email.service';

@Component({
  selector: 'app-reset-pass-email-form',
  templateUrl: './reset-pass-email-form.component.html',
  styleUrls: ['./reset-pass-email-form.component.scss']
})
export class ResetPassEmailFormComponent implements OnInit {
  resetPassEmailForm: FormGroup;
  message = '';
  loader = false;

  constructor(private formBuilder: FormBuilder, private emailService: EmailService) {
    this.resetPassEmailForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
    });
  }

  ngOnInit(): void {
  }

  requestPasswordReset(): any {
    const form = this.resetPassEmailForm.getRawValue();
    this.resetPassEmailForm.reset();
    this.loader = true;
    setTimeout(() => this.passwordReset(form), 1500);
  }

  private passwordReset(form: any): any {
    this.loader = false;
    this.emailService.requestPasswordReset(form,
      (data: any) => this.onDataLoaded(data),
      (err: any) => this.onError(err)
    );
  }

  private onDataLoaded(data: any): any {
    this.message = 'Please checkout your email to reset your password.';
  }

  private onError(err: any): any {
    this.message = 'Unknown error occurred. Please try again later.';
  }
}
