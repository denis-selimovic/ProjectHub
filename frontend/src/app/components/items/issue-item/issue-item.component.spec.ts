import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { IssueItemComponent } from './issue-item.component';

describe('OneIssueComponent', () => {
  let component: IssueItemComponent;
  let fixture: ComponentFixture<IssueItemComponent>;

  CommonTestingModule.setUpTestBed(IssueItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
