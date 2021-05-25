import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueDetailsFormComponent } from './issue-details-form.component';

describe('IssueDetailsFormComponent', () => {
  let component: IssueDetailsFormComponent;
  let fixture: ComponentFixture<IssueDetailsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IssueDetailsFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
