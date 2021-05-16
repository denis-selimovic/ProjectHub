import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';


@Component({
  selector: 'app-collaborators',
  templateUrl: './collaborators.component.html',
  styleUrls: ['./collaborators.component.scss']
})

export class CollaboratorsComponent implements OnInit {
  owner: User;
  collaborators: Array<User>;
  project: any;

  constructor() { 
  }

  ngOnInit(): void {
    this.project = {
      name: "NWT Project"
    }

     this.owner = {
      id: "12e08bf2-b4b2-4003-9288-507136ab459a",
      email: "ajsahaj@gmail.com",
      firstName: "Ajsa",
      lastName: "Hajradinovic"
    }

    this.collaborators = [
      {
        id: "13e08bf2-b4b2-4003-9288-507136ab459a",
        email: "lamijavrnjak@gmail.com",
        firstName: "Lamija",
        lastName: "Vrnjak"
      },
      {
        id: "14e08bf2-b4b2-4003-9288-507136ab459a",
        email: "amilazigo@gmail.com",
        firstName: "Amila",
        lastName: "Zigo"
      },
      {
        id: "15e08bf2-b4b2-4003-9288-507136ab459a",
        email: "denisselimovic@gmail.com",
        firstName: "Denis",
        lastName: "Selimovic"
      }
    ]
  }

}
