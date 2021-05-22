import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmailService } from '../../../../services/email/email.service';

@Component({
  selector: 'app-reset-password-new',
  templateUrl: './reset-password-new.component.html',
  styleUrls: ['./reset-password-new.component.scss']
})
export class ResetPasswordNewComponent implements OnInit {

  token: string | null = null;
  message = '';

  constructor(private route: ActivatedRoute, private router: Router, private emailService: EmailService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.token = params.token;
    });
  }

  onFormSubmit(form: any): any {
    const tokenForm = { ...form, token: this.token };
    this.emailService.resetPassword(tokenForm,
      (data: any) => {
        this.message = 'Password was successfully reset. Try to login now.';
        setTimeout(() => this.router.navigate(['/login']), 1000);
      },
      (err: any) => this.message = 'Error occurred. Try again later.'
    );
  }
}
