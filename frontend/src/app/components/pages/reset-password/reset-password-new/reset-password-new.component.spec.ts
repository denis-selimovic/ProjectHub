import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPasswordNewComponent } from './reset-password-new.component';

describe('ResetPasswordNewComponent', () => {
  let component: ResetPasswordNewComponent;
  let fixture: ComponentFixture<ResetPasswordNewComponent>;

  CommonTestingModule.setUpTestBed(ResetPasswordNewComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPasswordNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
