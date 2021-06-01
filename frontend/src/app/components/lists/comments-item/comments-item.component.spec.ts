import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CommentsItemComponent } from './comments-item.component';

describe('CommentsItemComponent', () => {
  let component: CommentsItemComponent;
  let fixture: ComponentFixture<CommentsItemComponent>;

  CommonTestingModule.setUpTestBed(CommentsItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentsItemComponent);
    component = fixture.componentInstance;
    component.comment = {
      id: "id",
      text: "This is text.",
      userId: "user id",
      userFirstName: "Ajsa",
      userLastName: "H",
      createdAt: new Date()
    };
    component.currentUser = {
      id: "id",
      firstName: "Ajsa",
      lastName: "Hajradinovic Ajsa",
      email: "ajsah@projecthub.com"
    }
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
