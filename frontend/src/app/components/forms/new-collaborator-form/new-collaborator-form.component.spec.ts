import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCollaboratorFormComponent } from './new-collaborator-form.component';

describe('NewCollaboratorFormComponent', () => {
  let component: NewCollaboratorFormComponent;
  let fixture: ComponentFixture<NewCollaboratorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCollaboratorFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCollaboratorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
