import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';
import { IssueService } from 'src/app/services/issue/issue.service';

import { IssuesTableComponent } from './issues-table.component';

describe('IssuesTableComponent', () => {
  let component: IssuesTableComponent;
  let fixture: ComponentFixture<IssuesTableComponent>;

  CommonTestingModule.setUpTestBed(IssuesTableComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssuesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
