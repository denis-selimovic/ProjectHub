import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { TaskDetailsComponent } from './task-details.component';

describe('TaskDetailsComponent', () => {
  let component: TaskDetailsComponent;
  let fixture: ComponentFixture<TaskDetailsComponent>;

  CommonTestingModule.setUpTestBed(TaskDetailsComponent);
  
  beforeEach(() => {
    fixture = TestBed.createComponent(TaskDetailsComponent);
    component = fixture.componentInstance;
    component.task = {
      id: "id",
      name: "Task",
      description: "Task description",
      userId: null,
      userFirstName: null,
      userLastName: null,
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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
