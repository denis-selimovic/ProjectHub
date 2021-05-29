import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewIssueFormComponent } from './new-issue-form.component';

describe('NewIssueFormComponent', () => {
  let component: NewIssueFormComponent;
  let fixture: ComponentFixture<NewIssueFormComponent>;

  CommonTestingModule.setUpTestBed(NewIssueFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewIssueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
