import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CommentsListComponent } from './comments-list.component';

describe('CommentsListComponent', () => {
  let component: CommentsListComponent;
  let fixture: ComponentFixture<CommentsListComponent>;

  CommonTestingModule.setUpTestBed(CommentsListComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentsListComponent);
    component = fixture.componentInstance;
    component.comments = [
      {
        id: "id",
        text: "This is text.",
        userId: "user id",
        userFirstName: "Ajsa",
        userLastName: "H",
        createdAt: new Date()
      }
    ];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
