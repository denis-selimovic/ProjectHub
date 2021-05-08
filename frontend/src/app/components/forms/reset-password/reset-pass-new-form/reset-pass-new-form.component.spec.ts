import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPassNewFormComponent } from './reset-pass-new-form.component';

describe('ResetPasswordNewFormComponent', () => {
  let component: ResetPassNewFormComponent;
  let fixture: ComponentFixture<ResetPassNewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetPassNewFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassNewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
