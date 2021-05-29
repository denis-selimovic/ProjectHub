import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router, RouterLinkWithHref } from '@angular/router';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { LoginFormComponent } from './login-form.component';

describe('LoginFormComponent', () => {
  let component: LoginFormComponent;
  let fixture: ComponentFixture<LoginFormComponent>;
  let form: FormGroup;
  let emailInput: AbstractControl;
  let passwordInput: AbstractControl;

  CommonTestingModule.setUpTestBed(LoginFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginFormComponent);
    component = fixture.componentInstance;
    form = component.loginForm;
    emailInput = form.controls.email;
    passwordInput = form.controls.password;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when email is empty', () => {
    emailInput.setValue('');
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email has invalid form', () => {;
    emailInput.setValue('invalid');
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when email field is empty', () => {
    expect(emailInput.errors.required).toBeTruthy();
    expect(emailInput.errors.pattern).toBeFalsy();
  })

  it('should test input errors when email is invalid', () => {
    emailInput.setValue('invalid');
    expect(emailInput.errors.email).toBeTruthy();
  })

  it('should test input errors when email is valid', () => {
    emailInput.setValue('test@example.com');
    let errors = emailInput.errors || {};
    expect(errors['required']).toBeFalsy();
    expect(errors['pattern']).toBeFalsy();
    expect(emailInput.valid).toBeTruthy();
  })

  it('should test input errors when password and email are empty', () => {
    passwordInput.setValue('');
    emailInput.setValue('');
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when password is empty', () => {
    passwordInput.setValue('');
    expect(passwordInput.errors.required).toBeTruthy();
    expect(passwordInput.valid).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when password is entered', () => {
    passwordInput.setValue('123456789');
    expect(passwordInput.valid).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test submit button when form is invalid', () => {
    emailInput.setValue('');
    passwordInput.setValue('');
    expect(fixture.debugElement.nativeElement.querySelector('button').disabled).toBeTruthy();
  })

  it('should test submit button when form is valid', () => {
    emailInput.setValue('test@example.com');
    passwordInput.setValue('123456789');
    fixture.detectChanges();
    expect(form.valid).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('button').disabled).toBeFalsy();
  })

  it('should call onSumbit method', fakeAsync(() => {
    emailInput.setValue('test@example.com');
    passwordInput.setValue('123456789');
    fixture.detectChanges();

    spyOn(component, 'onSubmit');
  
    let button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();
    tick();
    expect(component.onSubmit).toHaveBeenCalled();
  }));

  it('should test router link for reset password', () => {
    const linkDebugEl = fixture.debugElement.query(By.css('a.redirect-link.link-center'));
    const routerLinkInstance = linkDebugEl.injector.get(RouterLinkWithHref);
    expect(routerLinkInstance['commands']).toEqual(['/reset-password-email']);
    expect(routerLinkInstance['href']).toEqual('/reset-password-email');
  })

  it('should test router link for register', () => {
    const linkDebugEl = fixture.debugElement.query(By.css('#register'));
    const routerLinkInstance = linkDebugEl.injector.get(RouterLinkWithHref);
    expect(routerLinkInstance['commands']).toEqual(['/register']);
    expect(routerLinkInstance['href']).toEqual('/register');
  })
  
});
