import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CollaboratorService } from 'src/app/services/collaborator/collaborator.service';
import { Project, ProjectService } from 'src/app/services/project/project.service';
import { User, UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-collaborators',
  templateUrl: './collaborators.component.html',
  styleUrls: ['./collaborators.component.scss']
})

export class CollaboratorsComponent implements OnInit {
  currentUser: User;
  owner: User;
  isOwner: boolean = true;
  collaborators: Array<User> = [];
  project: Project;
  projectId: string;
  error: string;

  constructor(private userService: UserService, private projectService: ProjectService, private collaboratorService: CollaboratorService, private route: ActivatedRoute) { 
  }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();

    this.route.params.subscribe(params => {
      this.projectId = params.id;
    });

    this.loadProject();
    this.loadCollaborators();
  }

  loadProject(): void {
    this.projectService.getProjectById(this.projectId, 
      (data: any) => this.onProjectLoad(data),
      (err: any) => this.error = 'Error while loading data. Please try again later.');
  }

  loadUser(userId: string): void {
    this.userService.getUserById(userId,  
      (data: any) => this.onUserLoad(data), 
      () => this.error = 'Error while loading data. Please try again later.');
  }

  loadCollaborators(): void {
    this.collaboratorService.getCollaborators(this.projectId,
      (data: any) => this.onCollaboratorsLoad(data), 
      () => this.error = 'Error while loading data. Please try again later.');
  }

  onProjectLoad(data: any): any {
    this.project = data;
    this.loadUser(this.project.ownerId);
  }

  onUserLoad(data: any): any {
    this.owner = data;
    if (this.owner.id != this.currentUser.id)
      this.isOwner = false;
  }

  onCollaboratorsLoad(data: any): any {
    this.collaborators = data;
  }

  deleteCollaborator(id: any) {
    this.collaborators = this.collaborators.filter(i => i.id !== id);
    this.collaboratorService.deleteCollaborator(this.projectId, 
      id,
      (data: any) => {console.log(data)},
      (err: any ) => {console.log(err)});
  }

  addCollaborator(collaborator: User) {
    this.collaboratorService.createCollaborator(this.projectId, 
      {
        collaborator_id: collaborator.id
      },    
      (data: any) => this.loadCollaborators(),
      (err: any) => {console.log(err.error.errors.message)});
  }

}
