import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AbstractControl, FormGroup } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { RouterLinkWithHref } from '@angular/router';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPassEmailFormComponent } from './reset-pass-email-form.component';

describe('ResetPassEmailFormComponent', () => {
  let component: ResetPassEmailFormComponent;
  let fixture: ComponentFixture<ResetPassEmailFormComponent>;
  let form: FormGroup;
  let email: AbstractControl;
  let loader;

  CommonTestingModule.setUpTestBed(ResetPassEmailFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassEmailFormComponent);
    component = fixture.componentInstance;
    form = component.resetPassEmailForm;
    email = form.controls.email;
    loader = component.loader;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should disable reset password button when form is empty', () => {
    const resetPasswordButton = fixture.debugElement.queryAll(By.css('button')).find(
      buttonEl => buttonEl.nativeElement.textContent === 'Reset password'
    );
    expect(resetPasswordButton.nativeElement.disabled).toBeTruthy();
  })

  it('form invalid when empty', () => {
    expect(form.valid).toBeFalsy();
  });

  it('should test for form validity when email is empty', () => {
    email.setValue('');
    expect(form.valid).toBeFalsy();
    expect(email.valid).toBeFalsy();
    expect(email.errors.required).toBeTruthy();
  })

  it('should test for form validity when email has invalid form', () => {;
    email.setValue('invalid');
    expect(form.valid).toBeFalsy();
    expect(email.valid).toBeFalsy();
    expect(email.errors.email).toBeTruthy();
    expect(email.errors.required).toBeUndefined();
  })

  it('should test input errors when email is valid', () => {
    email.setValue('test@example.com');
    let errors = email.errors || {};
    expect(errors['required']).toBeFalsy();
    expect(errors['pattern']).toBeFalsy();
    expect(email.valid).toBeTruthy();
    expect(form.valid).toBeTruthy();
  })

  it('should enable reset password button when form is valid', () => {
    email.setValue('test@example.com');
    fixture.detectChanges();
    expect(form.valid).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('button').disabled).toBeFalsy();
  })

  it('should call requestPasswordReset method', fakeAsync(() => {
    email.setValue('test@example.com');
    fixture.detectChanges();

    spyOn(component, 'requestPasswordReset');
  
    let button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();
    tick();
    expect(component.requestPasswordReset).toHaveBeenCalled();
  }));

  it('should test router link for login', () => {
    const logInElement = fixture.debugElement.queryAll(By.css('a')).find(
      linkEl => linkEl.nativeElement.textContent === 'Log In'
    )
    const routerLinkInstance = logInElement.injector.get(RouterLinkWithHref);
    expect(routerLinkInstance['commands']).toEqual(['/login']);
    expect(routerLinkInstance['href']).toEqual('/login');
  })

  it('should test router link for register', () => {
    const registerElement = fixture.debugElement.queryAll(By.css('a')).find(
      linkEl => linkEl.nativeElement.textContent === 'Register'
    )
    const routerLinkInstance = registerElement.injector.get(RouterLinkWithHref);
    expect(routerLinkInstance['commands']).toEqual(['/register']);
    expect(routerLinkInstance['href']).toEqual('/register');
  })
});
