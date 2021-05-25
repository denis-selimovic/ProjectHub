import { Component, OnInit } from '@angular/core';
import { Notification } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrls: ['./notifications-list.component.scss']
})
export class NotificationsListComponent implements OnInit {

  notifications: Array<Notification> = [
      {id: "0", title: "Notification 0", description: "This notification has a very long description to see how does it look like on the ui....", dateTime: new Date(), read: false},
      {id: "1", title: "Notification 1", description: "This is a notification 1", dateTime: new Date(), read: true},
      {id: "2", title: "Notification 2", description: "This is a notification 2", dateTime: new Date(), read: true},
      {id: "2", title: "Notification 2", description: "This is a notification 2", dateTime: new Date(), read: false},
      {id: "2", title: "Notification 2", description: "This is a notification 2", dateTime: new Date(), read: false},
      {id: "2", title: "Notification 2", description: "This is a notification 2", dateTime: new Date(), read: true}
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
