import { Component, Input, OnInit } from '@angular/core';
import { Notification } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.component.scss']
})
export class NotificationItemComponent implements OnInit {

  @Input() notification: Notification;

  constructor() { }

  ngOnInit(): void {
  }

  getFormatedDate():string {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return this.notification.dateTime.toLocaleTimeString("en-US", options);
  }

}
