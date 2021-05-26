import { NgModule } from '@angular/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { LoginComponent } from './components/pages/login/login.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginFormComponent } from './components/forms/login-form/login-form.component';
import { ResetPasswordEmailComponent } from './components/pages/reset-password/reset-password-email/reset-password-email.component';
import { ResetPassEmailFormComponent } from './components/forms/reset-password/reset-pass-email-form/reset-pass-email-form.component';
import { ResetPasswordCodeComponent } from './components/pages/reset-password/reset-password-code/reset-password-code.component';
import { ResetPassCodeFormComponent } from './components/forms/reset-password/reset-pass-code-form/reset-pass-code-form.component';
import { ResetPasswordNewComponent } from './components/pages/reset-password/reset-password-new/reset-password-new.component';
import { ResetPassNewFormComponent } from './components/forms/reset-password/reset-pass-new-form/reset-pass-new-form.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { RegisterFormComponent } from './components/forms/register-form/register-form.component';
import { NavbarComponent } from './components/layout/navbar/navbar.component';
import { NewTaskFormComponent } from './components/forms/new-task-form/new-task-form.component';
import { NewTaskComponent } from './components/pages/new-task/new-task.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { NewProjectFormComponent } from './components/forms/new-project-form/new-project-form.component';
import { NewProjectComponent } from './components/pages/new-project/new-project.component';
import { NewIssueFormComponent } from './components/forms/new-issue-form/new-issue-form.component';
import { NewIssueComponent } from './components/pages/new-issue/new-issue.component';
import { NotFoundComponent } from './components/pages/not-found/not-found.component';
import { ProjectsComponent } from './components/pages/projects/projects.component';
import { ProjectsTableComponent } from './components/tables/projects-table/projects-table.component';
import { UserService } from './services/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import { GuardService } from './services/guard/guard.service';
import { CookieService } from './services/cookie/cookie.service';
import { TokenService } from './services/token/token.service';
import { ConfirmEmailComponent } from './components/pages/confirm-email/confirm-email.component';
import { EmailService } from './services/email/email.service';
import { IssuesComponent } from './components/pages/issues/issues.component';
import { IssueItemComponent } from './components/items/issue-item/issue-item.component';
import { IssuesTableComponent } from './components/tables/issues-table/issues-table.component';
import { LocalStorageService } from './services/local-storage/local-storage.service';
import { ProjectItemComponent } from './components/items/project-item/project-item.component';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from './components/modal/modal/modal.component';
import { ProjectDetailsComponent } from './components/project-details/project-details.component';
import { CollaboratorsComponent } from './components/pages/collaborators/collaborators.component';
import { CollaboratorsTableComponent } from './components/tables/collaborators-table/collaborators-table.component';
import { NewCollaboratorFormComponent } from './components/forms/new-collaborator-form/new-collaborator-form.component';
import { TasksComponent } from './components/pages/tasks/tasks.component';
import { TasksListComponent } from './components/lists/tasks-list/tasks-list.component';
import { TasksItemComponent } from './components/lists/tasks-item/tasks-item.component';
import { NewTaskModalComponent } from './components/dialogs/new-task-modal/new-task-modal.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TaskDetailsComponent } from './components/pages/task-details/task-details.component';
import { MatSelectModule } from '@angular/material/select';
import { CommentsListComponent } from './components/lists/comments-list/comments-list.component';
import { CommentsItemComponent } from './components/lists/comments-item/comments-item.component';
import { ConfirmDeletionComponent } from './components/dialogs/confirm-deletion/confirm-deletion.component';
import { CreateTaskModalComponent } from './components/modals/create-task-modal/create-task-modal.component';
import { CreateIssueModalComponent } from './components/modals/create-issue-modal/create-issue-modal.component';
import { MyAccountComponent } from './components/pages/my-account/my-account.component';
import { IssueDetailsFormComponent } from './components/forms/issue-details-form/issue-details-form.component';
import { ActionResultComponent } from './components/dialogs/action-result/action-result.component';

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
    NavbarComponent,
    NewTaskFormComponent,
    NewTaskComponent,
    NewProjectFormComponent,
    NewProjectComponent,
    NewIssueFormComponent,
    NewIssueComponent,
    NotFoundComponent,
    ProjectsComponent,
    ProjectsTableComponent,
    RegisterComponent,
    RegisterFormComponent,
    NotFoundComponent,
    DashboardComponent,
    ConfirmEmailComponent,
    IssuesComponent,
    IssueItemComponent,
    IssuesTableComponent,
    ProjectItemComponent,
    ProjectDetailsComponent,
    ModalComponent,
    ProjectDetailsComponent,
    CollaboratorsComponent,
    CollaboratorsTableComponent,
    NewCollaboratorFormComponent,
    ConfirmDeletionComponent,
    TasksComponent,
    TasksListComponent,
    TasksItemComponent,
    NewTaskModalComponent,
    TaskDetailsComponent,
    CommentsListComponent,
    CommentsItemComponent,
    ConfirmDeletionComponent,
    CreateTaskModalComponent,
    CreateIssueModalComponent,
    MyAccountComponent,
    IssueDetailsFormComponent,
    ActionResultComponent
  ],
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
    NgbModule
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
    MatDialogModule
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    ConfirmDeletionComponent,
    NewTaskModalComponent
  ]
})
export class AppModule { }
