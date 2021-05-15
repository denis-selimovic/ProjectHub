import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit {
  project: any
  owner: any // GET /api/v1/users/{ownerId}
  collaborators: any // GET /api/v1/projects/{projectId}/collaborators =>  GET /api/v1/users/{collaboratorId}
  isOwner: boolean

  constructor() { 
    this.isOwner = true;

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
        lastName: "Selimovics"
      },
    ]
  }

  ngOnInit(): void {
  }

}
