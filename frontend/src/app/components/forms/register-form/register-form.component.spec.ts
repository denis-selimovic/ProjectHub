import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { RegisterFormComponent } from './register-form.component';

describe('RegisterFormComponent', () => {
  let component: RegisterFormComponent;
  let fixture: ComponentFixture<RegisterFormComponent>;
  let form: FormGroup;
  let firstName;
  let lastName;
  let emailInput;
  let password;
  let confirmPassword;

  CommonTestingModule.setUpTestBed(RegisterFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterFormComponent);
    component = fixture.componentInstance;
    form = component.registerForm;
    firstName = form.controls.firstName;
    lastName = form.controls.lastName;
    emailInput = form.controls.email;
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

  it('should test for form validity when first name is empty', () => {
    firstName.setValue('');
    expect(firstName.valid).toBeFalsy();
    expect(firstName.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when first name is too long', () => {
    firstName.setValue('a'.repeat(33));
    expect(firstName.valid).toBeFalsy();
    expect(firstName.errors.required).toBeFalsy();
    expect(firstName.errors.maxlength).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when last name is empty', () => {
    lastName.setValue('');
    expect(lastName.valid).toBeFalsy();
    expect(lastName.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when last name is too long', () => {
    lastName.setValue('a'.repeat(33));
    expect(lastName.valid).toBeFalsy();
    expect(lastName.errors.required).toBeFalsy();
    expect(lastName.errors.maxlength).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email is empty', () => {
    emailInput.setValue('');
    expect(emailInput.valid).toBeFalsy();
    expect(emailInput.errors.required).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test for form validity when email has invalid form', () => {;
    emailInput.setValue('invalid');
    expect(emailInput.valid).toBeFalsy();
    expect(emailInput.errors.required).toBeFalsy();
    expect(emailInput.errors.email).toBeTruthy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input errors when email field is empty', () => {
    expect(emailInput.errors.required).toBeTruthy();
    expect(emailInput.errors.email).toBeFalsy();
  })

  it('should test input errors when email is valid', () => {
    emailInput.setValue('test@example.com');
    let errors = emailInput.errors || {};
    expect(errors['required']).toBeFalsy();
    expect(errors['email']).toBeFalsy();
    expect(emailInput.valid).toBeTruthy();
  })

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
    password.setValue('p@ssword');
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
    confirmPassword.setValue('Password1');
    expect(confirmPassword.errors.mustMatch).toBeTruthy();
    expect(confirmPassword.errors.required).toBeFalsy();
    expect(form.valid).toBeFalsy();
  })

  it('should test input erors when passwords don\'t match (confirm password field doesn\'t contain an uppercase)', () => {
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@ssword1');
    expect(confirmPassword.errors).toBeNull();
    expect(password.errors).toBeNull();
    expect(form.valid).toBeFalsy();
  })

  it('should test submit button when form is valid', () => {
    firstName.setValue('Amila');
    lastName.setValue('Zigo');
    emailInput.setValue('amila@gmail.com');
    password.setValue('P@ssword1');
    confirmPassword.setValue('P@ssword1');
    fixture.detectChanges();
    expect(form.valid).toBeTruthy();
  })

});
