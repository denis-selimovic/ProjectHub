import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AbstractControl, FormGroup } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { RouterLinkWithHref } from '@angular/router';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPassNewFormComponent } from './reset-pass-new-form.component';

describe('ResetPasswordNewFormComponent', () => {
  let component: ResetPassNewFormComponent;
  let fixture: ComponentFixture<ResetPassNewFormComponent>;
  let form: FormGroup;
  let password: AbstractControl;
  let confirmPassword: AbstractControl;

  CommonTestingModule.setUpTestBed(ResetPassNewFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassNewFormComponent);
    component = fixture.componentInstance;
    form = component.resetPassNewForm;
    password = form.controls.password;
    confirmPassword = form.controls.confirmPassword;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(form.valid).toBeFalsy();
  });

  it('should test input erors when password is empty', () => {
    password.setValue('');
    expect(password.valid).toBeFalsy();
    expect(password.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when password is too short', () => {
    password.setValue('1234567');
    expect(password.errors.minlength).toBeTruthy();
    expect(password.valid).toBeFalsy();
    expect(password.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();

    fixture.detectChanges();
    const errorText = fixture.debugElement.query(By.css('.error')).nativeElement;
    expect(errorText.textContent).toContain('Password must contain at least 8 characters with at least one lowercase, one uppercase, one digit and one special character');
  })

  it('should test input erors when password doesn\'t contain a lowercase', () => {
    password.setValue('P@SSWORD1');
    expect(password.valid).toBeFalsy();
    expect(password.errors.pattern).toBeTruthy();
    expect(password.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();

    fixture.detectChanges();
    const errorText = fixture.debugElement.query(By.css('.error')).nativeElement;
    expect(errorText.textContent).toContain('Password must contain at least 8 characters with at least one lowercase, one uppercase, one digit and one special character');
  })

  it('should test input erors when password doesn\'t contain an uppercase', () => {
    password.setValue('p@ssword1');
    expect(password.valid).toBeFalsy();
    expect(password.errors.pattern).toBeTruthy();
    expect(password.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();

    fixture.detectChanges();
    const errorText = fixture.debugElement.query(By.css('.error')).nativeElement;
    expect(errorText.textContent).toContain('Password must contain at least 8 characters with at least one lowercase, one uppercase, one digit and one special character');
  })

  it('should test input erors when password doesn\'t contain a number', () => {
    password.setValue('p@ssword');
    expect(password.valid).toBeFalsy();
    expect(password.errors.pattern).toBeTruthy();
    expect(password.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();

    fixture.detectChanges();
    const errorText = fixture.debugElement.query(By.css('.error')).nativeElement;
    expect(errorText.textContent).toContain('Password must contain at least 8 characters with at least one lowercase, one uppercase, one digit and one special character');
  })

  it('should test input erors when password doesn\'t contain a special character', () => {
    password.setValue('password');
    expect(password.valid).toBeFalsy();
    expect(password.errors.pattern).toBeTruthy();
    expect(password.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when password is valid', () => {
    password.setValue('P@ssword1');
    expect(password.valid).toBeTruthy();
    expect(password.errors).toBeNull();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when confirm password field is empty', () => {
    confirmPassword.setValue('');
    expect(confirmPassword.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when passwords don\'t match (confirm password field doesn\'t contain a special character)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('Password1');
    expect(confirmPassword.errors.mustMatch).toBeTruthy();
    expect(confirmPassword.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when passwords don\'t match (confirm password field doesn\'t contain a lowercase)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@SSWORD1');
    expect(confirmPassword.errors.mustMatch).toBeTruthy();
    expect(confirmPassword.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when passwords don\'t match (confirm password field doesn\'t contain an uppercase)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('p@ssword1');
    expect(confirmPassword.errors.mustMatch).toBeTruthy();
    expect(confirmPassword.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when passwords match)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@ssword1');
    expect(confirmPassword.errors).toBeNull();
    expect(password.errors).toBeNull();
  })

  it('should test for form validity when passwords match)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@ssword1');
    expect(form.valid).toBeTruthy();
  })

  it('should call submitForm method', fakeAsync(() => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@ssword1');
    fixture.detectChanges();

    spyOn(component, 'submitForm');
  
    let button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();
    tick();
    expect(component.submitForm).toHaveBeenCalled();
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
