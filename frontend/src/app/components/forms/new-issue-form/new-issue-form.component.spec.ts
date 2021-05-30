import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewIssueFormComponent } from './new-issue-form.component';

describe('NewIssueFormComponent', () => {
  let component: NewIssueFormComponent;
  let fixture: ComponentFixture<NewIssueFormComponent>;
  let form: FormGroup;

  CommonTestingModule.setUpTestBed(NewIssueFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewIssueFormComponent);
    component = fixture.componentInstance;

    component.projectName = "Project name";
    component.priorities = [
      {
        id: "id1",
        priority: "Critical"
      }, 
      {
        id: "id2",
        priority: "High"
      }, 
      {
        id: "id3",
        priority: "Medium"
      }, 
      {
        id: "id4",
        priority: "Low"
      }
    ];
    form = component.updateForm();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should test for form validity when name is too long', () => {
    const nameInput = form.controls.name;
    const descriptionInput = form.controls.description;
    form.controls.priority_id.setValue(component.priorities[0].id);

    descriptionInput.setValue('Regular description');
    nameInput.setValue('a'.repeat(51));

    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when description is too long', () => {
    const nameInput = form.controls.name;
    const descriptionInput = form.controls.description;
    form.controls.priority_id.setValue(component.priorities[0].id);

    descriptionInput.setValue('a'.repeat(256));
    nameInput.setValue('Regular name');

    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when name is empty', () => {
    const nameInput = form.controls.name;
    const descriptionInput = form.controls.description;
    form.controls.priority_id.setValue(component.priorities[0].id);

    descriptionInput.setValue('Regular description');

    expect(nameInput.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when description is empty', () => {
    const nameInput = form.controls.name;
    const descriptionInput = form.controls.description;
    form.controls.priority_id.setValue(component.priorities[0].id);

    nameInput.setValue('Regular name');

    expect(descriptionInput.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  });

  it('should test if submit button is disabled', () => {
    form.controls.priority_id.setValue(component.priorities[0].id);
    expect(fixture.debugElement.nativeElement.querySelector('button').disabled).toBeTruthy();
  });

  it('should test for valid form', () => {
    const nameInput = form.controls.name;
    const descriptionInput = form.controls.description;
    form.controls.priority_id.setValue(component.priorities[0].id);

    nameInput.setValue('Regular name');
    descriptionInput.setValue('Regular description');

    expect(form.valid).toBeTruthy();
  });


});
