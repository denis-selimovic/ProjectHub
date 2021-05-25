import { Injectable } from '@angular/core';

export interface Notification {
  id: string;
  title: string;
  description: string;
  dateTime: Date;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() { }
}
