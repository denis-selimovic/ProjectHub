import { Injectable } from '@angular/core';

export interface Notification {
  id: string;
  title: string;
  description: string;
  dateTime: Date;
  read: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() { }
}
