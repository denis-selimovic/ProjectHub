import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Notification, NotificationService} from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrls: ['./notifications-list.component.scss']
})
export class NotificationsListComponent implements OnInit {
  page = 0;
  size = 5;
  canScroll = true;
  notifications: Array<Notification> = [];

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.fetchNotifications();
  }

  onScroll(): any {
    if (!this.canScroll) { return; }
    this.page += 1;
    this.fetchNotifications();
  }

  private fetchNotifications(): any {
    this.notificationService.getNotifications(this.page, this.size,
      (data: any) => this.addNotifications(data),
      (err: any) => {}
    );
  }

  private addNotifications(data: any): any {
    this.canScroll = data.metadata.has_next;
    this.notifications = this.notifications.concat(data.data);
  }
}
