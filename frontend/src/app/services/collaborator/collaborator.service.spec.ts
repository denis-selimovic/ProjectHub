import { TestBed } from '@angular/core/testing';

import { HttpClientModule } from '@angular/common/http';

import { CollaboratorService } from './collaborator.service';
import { ProjectService } from '../project/project.service';

describe('CollaboratorService', () => {
  let service: CollaboratorService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(CollaboratorService);
    projectService = TestBed.inject(ProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // it('should use CollaboratorService', () => {
  //   let project;
  //   projectService.createProject({ name: "Project 1" }, 
  //   (data: any) => project = data,
  //   (err) => {});
  //   expect(service.createCollaborator(project.id, {})).toBeTruthy();
  // });

});
