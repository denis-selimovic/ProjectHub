import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-password-new',
  templateUrl: './reset-password-new.component.html',
  styleUrls: ['./reset-password-new.component.scss']
})
export class ResetPasswordNewComponent implements OnInit {

  token: string | null = null;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.token = params.token;
    });
  }

  onFormSubmit(form: any): any {
    const tokenForm = { ...form, token: this.token };
    console.log(tokenForm);
  }
}
