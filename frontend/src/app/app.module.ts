import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
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
    ResetPassNewFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
