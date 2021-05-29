import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CreateIssueModalComponent } from './create-issue-modal.component';

describe('CreateIssueModalComponent', () => {
  let component: CreateIssueModalComponent;
  let fixture: ComponentFixture<CreateIssueModalComponent>;

  CommonTestingModule.setUpTestBed(CreateIssueModalComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateIssueModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
