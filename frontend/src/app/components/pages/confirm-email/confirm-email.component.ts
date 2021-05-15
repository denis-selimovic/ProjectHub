import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.scss']
})
export class ConfirmEmailComponent implements OnInit {

  loader = true;
  success = false;
  failure = false;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const token = params.token;
      if (!token) {
        this.setFailure();
      }
      else {

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
