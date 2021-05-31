import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewTaskModalComponent } from './new-task-modal.component';

describe('NewTaskModalComponent', () => {
  let component: NewTaskModalComponent;
  let fixture: ComponentFixture<NewTaskModalComponent>;

  CommonTestingModule.setUpTestBed(NewTaskModalComponent);
  
  beforeEach(() => {
    fixture = TestBed.createComponent(NewTaskModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
