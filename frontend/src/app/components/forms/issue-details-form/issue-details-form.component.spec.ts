import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { IssueDetailsFormComponent } from './issue-details-form.component';

describe('IssueDetailsFormComponent', () => {
  let component: IssueDetailsFormComponent;
  let fixture: ComponentFixture<IssueDetailsFormComponent>;

  CommonTestingModule.setUpTestBed(IssueDetailsFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
