import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPasswordCodeComponent } from './reset-password-code.component';

describe('ResetPasswordCodeComponent', () => {
  let component: ResetPasswordCodeComponent;
  let fixture: ComponentFixture<ResetPasswordCodeComponent>;

  CommonTestingModule.setUpTestBed(ResetPasswordCodeComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
