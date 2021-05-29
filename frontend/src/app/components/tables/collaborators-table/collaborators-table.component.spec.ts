import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CollaboratorsTableComponent } from './collaborators-table.component';

describe('CollaboratorsTableComponent', () => {
  let component: CollaboratorsTableComponent;
  let fixture: ComponentFixture<CollaboratorsTableComponent>;

  CommonTestingModule.setUpTestBed(CollaboratorsTableComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CollaboratorsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
