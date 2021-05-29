import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewProjectFormComponent } from './new-project-form.component';

describe('NewProjectFormComponent', () => {
  let component: NewProjectFormComponent;
  let fixture: ComponentFixture<NewProjectFormComponent>;

  CommonTestingModule.setUpTestBed(NewProjectFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewProjectFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
