import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AbstractControl, FormGroup } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { IssueDetailsFormComponent } from './issue-details-form.component';

describe('IssueDetailsFormComponent', () => {
  let component: IssueDetailsFormComponent;
  let fixture: ComponentFixture<IssueDetailsFormComponent>;
  let form: FormGroup;
  let nameInput: AbstractControl;
  let descriptionInput: AbstractControl;

  CommonTestingModule.setUpTestBed(IssueDetailsFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueDetailsFormComponent);
    component = fixture.componentInstance;

    component.issue = {
      id: "123456789",
      projectId: "4af29499-13b4-43b0-8b1d-e5a99928a67d",
      name: "Issue name",
      description: "Issue description",
      priority: {
        id: "54d53505-f3b3-4f7c-8075-ae6ae873a8e9",
        priority: "HIGH"
      }
    }

    component.priorities = [
      {
        id: "61520fe7-3538-454f-9e49-1696c1c54a01", 
        priority: "CRITICAL"
      },
      {
        id: "54d53505-f3b3-4f7c-8075-ae6ae873a8e9", 
        priority: "HIGH"
      },
      {
        id: "d1a8ff7c-1093-4b57-8e31-b4225bdac032", 
        priority: "MEDIUM"
      },
      {
        id: "073e0ca4-7c53-4fb9-b794-eb6ce4e83224", 
        priority: "LOW"
      }
    ];

    component.ngOnChanges();
    form = component.issueDetailsForm;
    nameInput = form.controls.name;
    descriptionInput = form.controls.description;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show details of the issue', () => {
    expect(form.controls.name.value).toBe('Issue name');
    expect(form.controls.description.value).toBe('Issue description');
    expect(form.controls.priority_id.value).toBe('54d53505-f3b3-4f7c-8075-ae6ae873a8e9');
  })

  it('should test if edit button is disabled', () => {
    const editButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Edit issue'
    );
    expect(editButton.nativeElement.disabled).toBeTruthy();
  })

  it('should call close method', fakeAsync(() => {
    spyOn(component, 'close');
    const closeButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Close'
    );
    closeButton.nativeElement.click();
    tick();
    expect(component.close).toHaveBeenCalled();
  }))

  it('should test input errors when issue name is empty', () => {
    nameInput.setValue('');
    expect(nameInput.errors.required).toBeTruthy();
    expect(nameInput.valid).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when issue name is too long', () => {
    nameInput.setValue('a'.repeat(51));
    expect(nameInput.errors.required).toBeUndefined();
    expect(nameInput.errors.maxlength).toBeTruthy();
    expect(nameInput.valid).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when issue name is correct', () => {
    nameInput.setValue('This is a new issue name');
    expect(nameInput.errors).toBeNull();
    expect(nameInput.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();
  })

  it('should test input errors when issue description is empty', () => {
    descriptionInput.setValue('');
    expect(descriptionInput.errors.required).toBeTruthy();
    expect(descriptionInput.valid).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when issue description is too long', () => {
    descriptionInput.setValue('a'.repeat(256));
    expect(descriptionInput.errors.required).toBeUndefined();
    expect(descriptionInput.errors.maxlength).toBeTruthy();
    expect(descriptionInput.valid).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when issue description is correct', () => {
    descriptionInput.setValue('Correct description');
    expect(descriptionInput.errors).toBeNull();
    expect(descriptionInput.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();
  })

  it('should enable edit button when the issue name is changed', () => {
    form.controls.name.setValue("This is new issue name");
    form.markAsDirty();
    fixture.detectChanges();

    expect(form.controls.name.value).toBe("This is new issue name");
    expect(form.controls.name.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();

    const editButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Edit issue'
    );
    expect(editButton.nativeElement.disabled).toBeFalsy();
  })

  it('should enable edit button when the issue description is changed', () => {
    form.controls.description.setValue("This is new issue description");
    form.markAsDirty();
    fixture.detectChanges();

    expect(form.controls.description.value).toBe("This is new issue description");
    expect(form.controls.description.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();

    const editButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Edit issue'
    );
    expect(editButton.nativeElement.disabled).toBeFalsy();
  })

  it('should enable edit button when the issue priority is changed', () => {
    form.controls.priority_id.setValue(component.priorities[3]);
    form.markAsDirty();
    fixture.detectChanges();

    expect(form.controls.priority_id.value).toBe(component.priorities[3]);
    expect(form.controls.priority_id.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();

    const editButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Edit issue'
    );
    expect(editButton.nativeElement.disabled).toBeFalsy();
  })

  it('should call onSumbit method', fakeAsync(() => {
    form.controls.name.setValue("This is new issue name");
    form.controls.description.setValue("This is new issue description");
    form.controls.priority_id.setValue(component.priorities[2]);
    form.markAsDirty();
    fixture.detectChanges();

    spyOn(component, 'onSubmit');
  
    const editButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Edit issue'
    );
    editButton.nativeElement.click();
    tick();
    expect(component.onSubmit).toHaveBeenCalled();
  }));
});
