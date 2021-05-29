import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewCollaboratorFormComponent } from './new-collaborator-form.component';

describe('NewCollaboratorFormComponent', () => {
  let component: NewCollaboratorFormComponent;
  let fixture: ComponentFixture<NewCollaboratorFormComponent>;

  CommonTestingModule.setUpTestBed(NewCollaboratorFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCollaboratorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should test for form validity when email has invalid form', () => {
    const form = component.newCollaboratorForm;
    const emailInput = form.controls.email;
    emailInput.setValue('invalidmail');
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email is empty', () => {
    const form = component.newCollaboratorForm;
    const emailInput = form.controls.email;
    emailInput.setValue('');
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email is valid', () => {
    const form = component.newCollaboratorForm;
    const emailInput = form.controls.email;
    emailInput.setValue('validmail@projecthub.com');
    expect(form.valid).toBeTruthy();
  })
});
