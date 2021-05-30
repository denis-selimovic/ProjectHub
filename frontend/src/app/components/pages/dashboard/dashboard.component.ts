import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { TokenService } from 'src/app/services/token/token.service';
import { PusherService } from 'src/app/services/pusher/pusher.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {

  currentUser: any = null;
  token: any = null;
  channel: any = null;
  hasNotifications: boolean;

  constructor(private tokenService: TokenService, private userService: UserService,
              private pusher: PusherService) {
    this.currentUser = this.userService.getCurrentUser();
    this.token = this.tokenService.getAccessToken();
  }

  ngOnInit(): void {
    if (!this.currentUser || !this.token) { return; }
    if (this.channel) { return; }
    this.channel = this.pusher.subscribe(this.token, `private-${this.currentUser.id}`);
    this.channel.bind('notification', (data: any) => {
      this.hasNotifications = true;
    });
  }
}
