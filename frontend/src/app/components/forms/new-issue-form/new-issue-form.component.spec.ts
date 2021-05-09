import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewIssueFormComponent } from './new-issue-form.component';

describe('NewIssueFormComponent', () => {
  let component: NewIssueFormComponent;
  let fixture: ComponentFixture<NewIssueFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewIssueFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewIssueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
