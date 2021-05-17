import { Component, OnInit } from '@angular/core';
import { Project } from 'src/app/models/Project';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  projects: Array<Project>;

  constructor() {
   }

  ngOnInit(): void {
    this.projects = [
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "First project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Second project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Third project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Fourth project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Fifth project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Sixth project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      },
      {
        id: "e7165476-b729-11eb-8529-0242ac130003",
        name: "Seventh project",
        ownerId: "a9328394-b729-11eb-8529-0242ac130003"
      }
    ]
  }

}
