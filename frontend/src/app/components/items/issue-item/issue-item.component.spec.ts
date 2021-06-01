import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { IssueItemComponent } from './issue-item.component';

describe('IssueItemComponent', () => {
  let component: IssueItemComponent;
  let fixture: ComponentFixture<IssueItemComponent>;
  let issue;

  CommonTestingModule.setUpTestBed(IssueItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueItemComponent);
    component = fixture.componentInstance;
    issue = {
      id: "02d39b6c-d84a-4707-9ed3-478086f87be8",
      name: "Issue name",
      description: "Issue description",
      projectId: "49e1614e-3f1d-4c90-9df5-f2a3316b9a13",
      priority: {}
    }
  });

  it('should create', () => {
    issue.priority = {
        id: "61520fe7-3538-454f-9e49-1696c1c54a01",
        priority_type: "CRITICAL"
    }
    component.issue = issue;
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should show issue name', () => {
    issue.priority = {
      id: "61520fe7-3538-454f-9e49-1696c1c54a01",
      priority_type: "CRITICAL"
    }
    component.issue = issue;
    fixture.detectChanges();
    let issueName = fixture.debugElement.query(By.css('h3')).nativeElement;
    expect(issueName.innerHTML).toBe("Issue name");
  })

  it('should call detailsClicked method', fakeAsync(() => {
    spyOn(component, 'detailsClicked');
  
    const detailsButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Details'
    );
    detailsButton.nativeElement.click();
    tick();
    expect(component.detailsClicked).toHaveBeenCalled();
  }));

  it('should call openModal method', fakeAsync(() => {
    spyOn(component, 'createTaskFromIssue');
  
    const taskButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Create task'
    );
    taskButton.nativeElement.click();
    tick();
    expect(component.createTaskFromIssue).toHaveBeenCalled();
  }));

  it('should call removeIssue method', fakeAsync(() => {
    spyOn(component, 'removeIssue');
  
    const removeIcon = fixture.debugElement.query(By.css('i')).nativeElement;
    removeIcon.click();
    tick();
    expect(component.removeIssue).toHaveBeenCalled();
  }));

  it('should display critical priority icon', () => {
    issue.priority = {
      id: "61520fe7-3538-454f-9e49-1696c1c54a01",
      priority_type: "CRITICAL"
    }
    component.issue = issue;
    fixture.detectChanges();
    const el = fixture.debugElement.nativeElement.querySelectorAll('img');
    expect(el[0]['src']).toContain('critical.png');
  })

  it('should display high priority icon', () => {
    issue.priority = {
      id: "61520fe7-3538-454f-9e49-1696c1c54a01",
      priority_type: "HIGH"
    }
    component.issue = issue;
    fixture.detectChanges();
    const el = fixture.debugElement.nativeElement.querySelectorAll('img');
    expect(el[0]['src']).toContain('high.png');
  })

  it('should display medium priority icon', () => {
    issue.priority = {
      id: "d1a8ff7c-1093-4b57-8e31-b4225bdac032",
      priority_type: "MEDIUM"
    }
    component.issue = issue;
    fixture.detectChanges();
    const el = fixture.debugElement.nativeElement.querySelectorAll('img');
    expect(el[0]['src']).toContain('medium.png');
  })

  it('should display low priority icon', () => {
    issue.priority = {
      id: "073e0ca4-7c53-4fb9-b794-eb6ce4e83224",
      priority_type: "LOW"
    }
    component.issue = issue;
    fixture.detectChanges();
    const el = fixture.debugElement.nativeElement.querySelectorAll('img');
    expect(el[0]['src']).toContain('low.png');
  })

});
