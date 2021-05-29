import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NewIssueComponent } from './new-issue.component';

describe('NewIssueComponent', () => {
  let component: NewIssueComponent;
  let fixture: ComponentFixture<NewIssueComponent>;

  CommonTestingModule.setUpTestBed(NewIssueComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NewIssueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
