import { Injectable } from '@angular/core';
import Pusher from 'pusher-js';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PusherService {

  private pusher: any = null;

  constructor() { }

  configure(token): any {
    if (this.pusher) { return; }
    this.pusher = new Pusher(environment.pusherKey, {
      cluster: environment.pusherCluster,
      authEndpoint: `${environment.api}/api/v1/notifications/socket-subscribe`,
      auth: {
        headers: {
          Authorization: token
        }
      }
    });
  }

  subscribe(token, channel): any {
    this.configure(token);
    return this.pusher?.subscribe(channel);
  }
}
