import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPassNewFormComponent } from './reset-pass-new-form.component';

describe('ResetPasswordNewFormComponent', () => {
  let component: ResetPassNewFormComponent;
  let fixture: ComponentFixture<ResetPassNewFormComponent>;

  CommonTestingModule.setUpTestBed(ResetPassNewFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassNewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
