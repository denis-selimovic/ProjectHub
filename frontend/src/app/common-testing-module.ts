import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatDialogModule } from "@angular/material/dialog";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule, NoopAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { MDBBootstrapModule } from "angular-bootstrap-md";
import { InfiniteScrollModule } from "ngx-infinite-scroll";
import { AppRoutingModule } from "./app-routing.module";
import { CookieService } from "./services/cookie/cookie.service";
import { EmailService } from "./services/email/email.service";
import { GuardService } from "./services/guard/guard.service";
import { LocalStorageService } from "./services/local-storage/local-storage.service";
import { NotificationService } from "./services/notification/notification.service";
import { TokenService } from "./services/token/token.service";
import { UserService } from "./services/user/user.service";

export class CommonTestingModule {
   public static setUpTestBed = (TestingComponent: any) => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [
          BrowserModule,
          CommonModule,
          AppRoutingModule,
          FormsModule,
          ReactiveFormsModule,
          MatInputModule,
          BrowserAnimationsModule,
          NoopAnimationsModule,
          MatPaginatorModule,
          HttpClientModule,
          MatTableModule,
          MatDialogModule,
          MDBBootstrapModule.forRoot(),
          NgbModule,
          HttpClientModule,
          MatDialogModule,
          MatSelectModule,
          NgbModule,
          InfiniteScrollModule
        ],
        providers: [
          UserService,
          GuardService,
          CookieService,
          TokenService,
          EmailService,
          LocalStorageService,
          MatTableModule,
          MatPaginatorModule,
          MatDialogModule,
          NotificationService
        ]
      });
    });
  }
}