import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { NotificationItemComponent } from './notification-item.component';

describe('NotificationItemComponent', () => {
  let component: NotificationItemComponent;
  let fixture: ComponentFixture<NotificationItemComponent>;

  CommonTestingModule.setUpTestBed(NotificationItemComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationItemComponent);
    component = fixture.componentInstance;
    component.notification = {
      id: "id",
      title: "Notification 1",
      description: "This is one notification.",
      read: true,
      created_at: new Date(),
      updated_at: new Date()
    }
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
