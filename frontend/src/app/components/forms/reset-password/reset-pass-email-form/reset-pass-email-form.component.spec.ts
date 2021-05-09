import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPassEmailFormComponent } from './reset-pass-email-form.component';

describe('ResetPassEmailFormComponent', () => {
  let component: ResetPassEmailFormComponent;
  let fixture: ComponentFixture<ResetPassEmailFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetPassEmailFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassEmailFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
