import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';
import { CommonTestingModule } from 'src/app/common-testing.module';
import { NewTaskFormComponent } from './new-task-form.component';

describe('NewTaskFormComponent', () => {
  let component: NewTaskFormComponent;
  let fixture: ComponentFixture<NewTaskFormComponent>;
  let form: FormGroup;

  CommonTestingModule.setUpTestBed(NewTaskFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewTaskFormComponent);
    component = fixture.componentInstance;

    component.project = {
      id: "id",
      name: "Project 1"
    };

    component.priorities =  [
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

    component.types = [
      {
        id: "id",
        type: "SPIKE",
      },
      {
        id: "id",
        type: "BUG"
      },
      {
        id: "id",
        type: "EPIC"
      },
      {
        id: "id",
        type: "STORY"
      },
      {
        id: "id",
        type: "CHANGE"
      }
    ];

    component.collaborators = [
      {
        id: "id",
        collaborator: {
          id: "id",
          firstName: "Ime",
          lastName: "Prezime",
          email: "imeprezime@projecthub.com"
        }
      }
    ];
    form = component.newTaskForm;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should test for form validity when name is too long', () => {
    const nameInput = form.controls.taskName;
    const descriptionInput = form.controls.description;
    descriptionInput.setValue('Regular description');
    nameInput.setValue('a'.repeat(51));
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when inputs are empty', () => {
    const descriptionInput = form.controls.description;
    const nameInput = form.controls.taskName;
    expect(descriptionInput.errors.required).toBeTruthy();
    expect(nameInput.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when inputs are valid', () => {
    const nameInput = form.controls.taskName;
    const descriptionInput = form.controls.description;
    descriptionInput.setValue('Regular description');
    nameInput.setValue('Regular name');
    form.controls.priority.setValue(component.priorities[0]);
    form.controls.type.setValue(component.types[0]);
    form.controls.collaborator.setValue(component.collaborators[0]);
    expect(form.valid).toBeTruthy();
  })
});
