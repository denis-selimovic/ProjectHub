import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPasswordEmailComponent } from './reset-password-email.component';

describe('ResetPasswordEmailComponent', () => {
  let component: ResetPasswordEmailComponent;
  let fixture: ComponentFixture<ResetPasswordEmailComponent>;

  CommonTestingModule.setUpTestBed(ResetPasswordEmailComponent);
  
  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
