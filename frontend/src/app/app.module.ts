import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/pages/login/login.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginFormComponent } from './components/forms/login-form/login-form.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { RegisterFormComponent } from './components/forms/register-form/register-form.component';
import { NewTaskFormComponent } from './components/forms/new-task-form/new-task-form.component';
import { NewTaskComponent } from './components/pages/new-task/new-task.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { NewProjectFormComponent } from './components/forms/new-project-form/new-project-form.component';
import { NewProjectComponent } from './components/pages/new-project/new-project.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    LoginFormComponent,
    RegisterComponent,
    RegisterFormComponent,
    NewTaskFormComponent,
    NewTaskComponent,
    NewProjectFormComponent,
    NewProjectComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NoopAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
