import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatInputModule} from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/pages/login/login.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginFormComponent } from './components/forms/login-form/login-form.component';
import { ResetPasswordEmailComponent } from './components/pages/reset-password/reset-password-email/reset-password-email.component';
import { ResetPassEmailFormComponent } from './components/forms/reset-password/reset-pass-email-form/reset-pass-email-form.component';
import { ResetPasswordCodeComponent } from './components/pages/reset-password/reset-password-code/reset-password-code.component';
import { ResetPassCodeFormComponent } from './components/forms/reset-password/reset-pass-code-form/reset-pass-code-form.component';
import { ResetPasswordNewComponent } from './components/pages/reset-password/reset-password-new/reset-password-new.component';
import { ResetPassNewFormComponent } from './components/forms/reset-password/reset-pass-new-form/reset-pass-new-form.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { RegisterFormComponent } from './components/forms/register-form/register-form.component';
import { NewTaskFormComponent } from './components/forms/new-task-form/new-task-form.component';
import { NewTaskComponent } from './components/pages/new-task/new-task.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { NewProjectFormComponent } from './components/forms/new-project-form/new-project-form.component';
import { NewProjectComponent } from './components/pages/new-project/new-project.component';
import { NewIssueFormComponent } from './components/forms/new-issue-form/new-issue-form.component';
import { NewIssueComponent } from './components/pages/new-issue/new-issue.component';
import { NotFoundComponent } from './components/pages/not-found/not-found.component';
import { UserService } from './services/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import { GuardService } from './services/guard/guard.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    LoginFormComponent,
    ResetPasswordEmailComponent,
    ResetPassEmailFormComponent,
    ResetPasswordCodeComponent,
    ResetPassCodeFormComponent,
    ResetPasswordNewComponent,
    ResetPassNewFormComponent,
    RegisterComponent,
    RegisterFormComponent,
    NewTaskFormComponent,
    NewTaskComponent,
    NewProjectFormComponent,
    NewProjectComponent,
    NewIssueFormComponent,
    NewIssueComponent,
    NotFoundComponent,
    RegisterComponent,
    RegisterFormComponent,
    NotFoundComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    HttpClientModule
  ],
  providers: [
    UserService,
    GuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
