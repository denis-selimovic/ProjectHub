import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewCollaboratorFormComponent } from './new-collaborator-form.component';

describe('NewCollaboratorFormComponent', () => {
  let component: NewCollaboratorFormComponent;
  let fixture: ComponentFixture<NewCollaboratorFormComponent>;
  let form: FormGroup;

  CommonTestingModule.setUpTestBed(NewCollaboratorFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCollaboratorFormComponent);
    component = fixture.componentInstance;
    form = component.newCollaboratorForm;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should test for form validity when email has invalid form', () => {
    const emailInput = form.controls.email;
    emailInput.setValue('invalidmail');
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email is empty', () => {
    const emailInput = form.controls.email;
    emailInput.setValue('');
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email is valid', () => {
    const emailInput = form.controls.email;
    emailInput.setValue('validmail@projecthub.com');
    expect(form.valid).toBeTruthy();
  })

  it('should test input errors when email field is empty', () => {
    const emailInput = form.controls.email;
    expect(emailInput.errors.required).toBeTruthy();
  })

  it('should test input errors when email is invalid', () => {
    const emailInput = form.controls.email;
    emailInput.setValue('invalidmail');
    expect(emailInput.errors.email).toBeTruthy();
  })


  it('should test submit button', () => {
    const emailInput = form.controls.email;
    emailInput.setValue('invalidmail');
    expect(fixture.debugElement.nativeElement.querySelector('button').disabled).toBeTruthy();
  })
  


});
