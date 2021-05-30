import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewProjectFormComponent } from './new-project-form.component';

describe('NewProjectFormComponent', () => {
  let component: NewProjectFormComponent;
  let fixture: ComponentFixture<NewProjectFormComponent>;
  let form: FormGroup;

  CommonTestingModule.setUpTestBed(NewProjectFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewProjectFormComponent);
    component = fixture.componentInstance;
    form = component.newProjectForm;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should test for form validity when name is empty', () => {
    const nameInput = form.controls.name;
    expect(nameInput.errors.required).toBeTruthy();    
  });

  it('should test for form validity when name is too long', () => {
    const nameInput = form.controls.name;
    nameInput.setValue('a'.repeat(51));
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when name is valid', () => {
    const nameInput = form.controls.name;
    nameInput.setValue('Project 1');
    expect(form.valid).toBeTruthy();
  });


});
