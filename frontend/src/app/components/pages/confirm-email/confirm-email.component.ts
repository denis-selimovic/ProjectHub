import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmailService } from '../../../services/email/email.service';

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.scss']
})
export class ConfirmEmailComponent implements OnInit {

  loader = true;
  success = false;
  failure = false;

  constructor(private route: ActivatedRoute, private emailService: EmailService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const token = params.token;
      if (!token) {
        this.setFailure();
      }
      else {
        this.emailService.confirmEmail(token,
          (body: any) => this.setSuccess(), (error: any) => this.setFailure());
      }
    });
  }

  setLoader(): void {
    this.loader = true;
    this.success = false;
    this.failure = false;
  }

  setSuccess(): void {
    this.loader = false;
    this.success = true;
    this.failure = false;
  }

  setFailure(): void {
    this.loader = false;
    this.success = false;
    this.failure = true;
  }
}
