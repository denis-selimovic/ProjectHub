import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { TaskDetailsComponent } from './task-details.component';

describe('TaskDetailsComponent', () => {
  let component: TaskDetailsComponent;
  let fixture: ComponentFixture<TaskDetailsComponent>;
  let rightForm: FormGroup;
  let leftForm: FormGroup;

  CommonTestingModule.setUpTestBed(TaskDetailsComponent);
  
  beforeEach(() => {
    fixture = TestBed.createComponent(TaskDetailsComponent);
    component = fixture.componentInstance;
    component.task = {
      id: "id",
      name: "Task",
      description: "Task description",
      userId: "123456789",
      userFirstName: "Amila",
      userLastName: "Zigo",
      projectId: "project id",
      projectName: "Project",
      priority: {
        id: "priority id",
        priority: "HIGH"
      },
      type: {
        id: "type id",
        type: "BUG"
      },
      status: {
        id: "status id",
        status: "OPEN"
      },
      createdAt: new Date(),
      updatedAt: new Date()
    };
    component.currentUser = {
      id: "123456789",
      firstName: "Amila",
      lastName: "Zigo",
      email: "azigo1@etf.unsa.ba"
    }
    component.collaborators = [{
        id: "123456789",
        project: {
          id: "123123123",
          name: "Project name",
          ownerId: "123456789",
          createdAt: new Date(),
          updatedAt: new Date()
        },
        collaboratorId: "123456789",
        collaborator: {
          id: "123123123",
          firstName: "Collaborator",
          lastName: "Collaboratovic",
          email: "colab@colaboratovic.ba"
        }
      }
    ]
    leftForm = component.leftForm;
    rightForm = component.rightForm;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
