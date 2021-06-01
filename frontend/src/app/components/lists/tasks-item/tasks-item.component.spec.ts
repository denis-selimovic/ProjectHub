import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { TasksItemComponent } from './tasks-item.component';

describe('TasksItemComponent', () => {
  let component: TasksItemComponent;
  let fixture: ComponentFixture<TasksItemComponent>;

  CommonTestingModule.setUpTestBed(TasksItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(TasksItemComponent);
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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
