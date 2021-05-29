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
});
