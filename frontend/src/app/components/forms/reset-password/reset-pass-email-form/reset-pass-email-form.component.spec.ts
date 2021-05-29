import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPassEmailFormComponent } from './reset-pass-email-form.component';

describe('ResetPassEmailFormComponent', () => {
  let component: ResetPassEmailFormComponent;
  let fixture: ComponentFixture<ResetPassEmailFormComponent>;

  CommonTestingModule.setUpTestBed(ResetPassEmailFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassEmailFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
