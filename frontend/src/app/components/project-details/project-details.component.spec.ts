import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ProjectDetailsComponent } from './project-details.component';

describe('ProjectDetailsComponent', () => {
  let component: ProjectDetailsComponent;
  let fixture: ComponentFixture<ProjectDetailsComponent>;

  CommonTestingModule.setUpTestBed(ProjectDetailsComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
