import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  links: Array<{ text: string, path: string }>;

  constructor() { 
    this.links = new Array();
    this.links.push({text: "projects", path: ""});
    this.links.push({text: "my account", path: ""});
  }

  ngOnInit(): void {
  }

}
