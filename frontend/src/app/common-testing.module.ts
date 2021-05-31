import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { DatePipe } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TestBed } from '@angular/core/testing';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { UserService } from './services/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { GuardService } from './services/guard/guard.service';
import { CookieService } from './services/cookie/cookie.service';
import { TokenService } from './services/token/token.service';
import { EmailService } from './services/email/email.service';
import { LocalStorageService } from './services/local-storage/local-storage.service';
import { CommonModule } from '@angular/common';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { NotificationService } from './services/notification/notification.service';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MockUserService } from './mock-services/mock-user.service';

@NgModule({
    declarations: []
  })
  export class CommonTestingModule {
  
    public static setUpTestBed = (TestingComponent: any) => {
      beforeEach(() => {
        TestBed.configureTestingModule({
          imports: [
            ReactiveFormsModule,
            FormsModule,
            HttpClientTestingModule,
            RouterTestingModule,
            BrowserModule,
            CommonModule,
            AppRoutingModule,
            MatInputModule,
            BrowserAnimationsModule,
            NoopAnimationsModule,
            MatPaginatorModule,
            HttpClientModule,
            MatTableModule,
            MatDialogModule,
            MDBBootstrapModule.forRoot(),
            NgbModule,
            MatDialogModule,
            MatSelectModule,
            InfiniteScrollModule
          ],
          providers: [
            DatePipe,
            {
              provide: UserService,
              useClass: MockUserService
            },
            GuardService,
            CookieService,
            TokenService,
            EmailService,
            LocalStorageService,
            MatTableModule,
            MatPaginatorModule,
            MatDialogModule,
            NotificationService,
            HttpClientModule,
            NgbActiveModal
          ],
          declarations: [TestingComponent],
          schemas: [CUSTOM_ELEMENTS_SCHEMA]
        });
      });
    }

  public static getElements(element: Element, query: string): HTMLElement[] {
    return [].slice.call(element.querySelectorAll(query));
  }

  public static getRows(tableElement: Element): HTMLElement[] {
    return this.getElements(tableElement, '.mat-row');
  }

  public static getCells(row: Element): HTMLElement[] {
      if (!row) {
        return [];
      }
      let cells = this.getElements(row, 'mat-cell');
      if (!cells.length) {
        cells = this.getElements(row, 'td.mat-cell');
      }
      return cells;
    }
  }