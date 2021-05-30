import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { IssueItemComponent } from './issue-item.component';

describe('IssueItemComponent', () => {
  let component: IssueItemComponent;
  let fixture: ComponentFixture<IssueItemComponent>;

  CommonTestingModule.setUpTestBed(IssueItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueItemComponent);
    component = fixture.componentInstance;
    component.issue = {
      id: "123456789",
      name: "Issue name",
      description: "Issue description",
      projectId: "555555",
      priority: {
        id: "987654321",
        priority_type: "HIGH"
      }
    }
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
