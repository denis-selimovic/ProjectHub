import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewTaskFormComponent } from './new-task-form.component';

describe('NewTaskFormComponent', () => {
  let component: NewTaskFormComponent;
  let fixture: ComponentFixture<NewTaskFormComponent>;

  CommonTestingModule.setUpTestBed(NewTaskFormComponent);
  beforeEach(() => {
    fixture = TestBed.createComponent(NewTaskFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
