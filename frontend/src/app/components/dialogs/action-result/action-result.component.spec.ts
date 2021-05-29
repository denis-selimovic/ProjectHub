import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Action } from 'rxjs/internal/scheduler/Action';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ActionResultComponent } from './action-result.component';

describe('ActionResultComponent', () => {
  let component: ActionResultComponent;
  let fixture: ComponentFixture<ActionResultComponent>;

  CommonTestingModule.setUpTestBed(ActionResultComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
