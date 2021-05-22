import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login.component';
import { ResetPasswordEmailComponent } from './components/pages/reset-password/reset-password-email/reset-password-email.component'
import { ResetPasswordCodeComponent } from './components/pages/reset-password/reset-password-code/reset-password-code.component'
import { ResetPasswordNewComponent } from './components/pages/reset-password/reset-password-new/reset-password-new.component'
import { NotFoundComponent } from './components/pages/not-found/not-found.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { NewTaskComponent } from './components/pages/new-task/new-task.component';
import { NewIssueComponent } from './components/pages/new-issue/new-issue.component';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import { ConfirmEmailComponent } from './components/pages/confirm-email/confirm-email.component';
import { GuardService } from './services/guard/guard.service';
import { IssuesComponent } from './components/pages/issues/issues.component';
import {ProjectDetailsComponent} from './components/pages/project-details/project-details.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/login'
  },
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    pathMatch: 'full',
    component: DashboardComponent,
    canActivate: [GuardService],
  },
  {
    path: 'reset-password-email',
    pathMatch: 'full',
    component: ResetPasswordEmailComponent
  },
  {
    path: 'reset-password-code',
    pathMatch: 'full',
    component: ResetPasswordCodeComponent
  },
  {
    path: 'reset-password-new',
    pathMatch: 'full',
    component: ResetPasswordNewComponent
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegisterComponent
  },
  {
    path: 'new-task',
    component: NewTaskComponent
  },
  {
    path: 'new-issue',
    component: NewIssueComponent
  },
  {
    path: 'not-found',
    pathMatch: 'full',
    component: NotFoundComponent
  },
  {
    path: 'projects/:id',
    component: ProjectDetailsComponent,
    canActivate: [GuardService]
  },
  {
    path: 'confirm-email/:token',
    pathMatch: 'full',
    component: ConfirmEmailComponent
  },
  {
    path: 'issues',
    component: IssuesComponent
  },
  {
    path: '**',
    redirectTo: '/not-found'
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
