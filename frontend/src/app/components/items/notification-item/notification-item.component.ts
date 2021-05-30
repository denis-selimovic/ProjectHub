import { Component, Input, OnInit } from '@angular/core';
import {Notification, NotificationService} from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.component.scss']
})
export class NotificationItemComponent implements OnInit {

  @Input() notification: Notification;

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  markAsRead(id: string): any {
    this.notificationService.markAsRead(id, (data: any) => this.update(data), err => {});
  }

  private update(data): any {
    this.notification = data.data;
  }
}
