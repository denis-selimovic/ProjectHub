import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPassCodeFormComponent } from './reset-pass-code-form.component';

describe('ResetPassCodeFormComponent', () => {
  let component: ResetPassCodeFormComponent;
  let fixture: ComponentFixture<ResetPassCodeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetPassCodeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassCodeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
