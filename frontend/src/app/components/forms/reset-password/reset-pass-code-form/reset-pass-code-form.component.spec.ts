import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ResetPassCodeFormComponent } from './reset-pass-code-form.component';

describe('ResetPassCodeFormComponent', () => {
  let component: ResetPassCodeFormComponent;
  let fixture: ComponentFixture<ResetPassCodeFormComponent>;

  CommonTestingModule.setUpTestBed(ResetPassCodeFormComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassCodeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
