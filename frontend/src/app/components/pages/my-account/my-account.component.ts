import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {
  title: string = "Change password";
  links: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
