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
import { ProjectDetailsComponent } from './components/project-details/project-details.component';
import { CollaboratorsComponent } from './components/pages/collaborators/collaborators.component';
import { TasksComponent } from './components/pages/tasks/tasks.component';
import { TaskDetailsComponent } from './components/pages/task-details/task-details.component';
import { MyAccountComponent } from './components/pages/my-account/my-account.component';

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
    path: 'reset-password/:token',
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
    path: 'collaborators',
    component: CollaboratorsComponent
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
    path: 'tasks',
    pathMatch: 'full',
    component: TasksComponent
  },
  {
    path: 'tasks/details',
    pathMatch: 'full',
    component: TaskDetailsComponent
  },
  {
    path: 'my-account',
    pathMatch: 'full',
    component: MyAccountComponent,
    canActivate: [GuardService]
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
