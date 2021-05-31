import {Component, OnDestroy, OnInit} from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { TokenService } from 'src/app/services/token/token.service';
import { PusherService } from 'src/app/services/pusher/pusher.service';
import {NotificationService} from '../../../services/notification/notification.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, OnDestroy {

  currentUser: any = null;
  token: any = null;
  channel: any = null;
  hasNotifications: boolean;

  constructor(private tokenService: TokenService, private userService: UserService,
              private notificationService: NotificationService, private pusher: PusherService) {
    this.currentUser = this.userService.getCurrentUser();
    this.token = this.tokenService.getAccessToken();
  }

  ngOnInit(): void {
    if (!this.currentUser || !this.token) { return; }
    this.notificationService.getNotifications(0, 1,
      (data: any) => this.showNotifications(data),
      (err: any) => this.hasNotifications = false);
    this.channel = this.pusher.subscribe(this.token, `private-${this.currentUser.id}`);
    this.channel.bind('notification', (data: any) => {
      this.hasNotifications = true;
    });
  }

  ngOnDestroy(): void {
  }

  private showNotifications(data: any): any {
    if (data.data.length === 0) {
      this.hasNotifications = false;
      return;
    }
    this.hasNotifications = !data.data[0].read;
  }
}
