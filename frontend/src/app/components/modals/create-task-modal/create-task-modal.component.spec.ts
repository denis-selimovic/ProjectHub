import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CreateTaskModalComponent } from './create-task-modal.component';

describe('CreateTaskModalComponent', () => {
  let component: CreateTaskModalComponent;
  let fixture: ComponentFixture<CreateTaskModalComponent>;

  CommonTestingModule.setUpTestBed(CreateTaskModalComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTaskModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
