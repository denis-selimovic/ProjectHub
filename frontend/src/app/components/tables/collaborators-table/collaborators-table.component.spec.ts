import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollaboratorsTableComponent } from './collaborators-table.component';

describe('CollaboratorsTableComponent', () => {
  let component: CollaboratorsTableComponent;
  let fixture: ComponentFixture<CollaboratorsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CollaboratorsTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CollaboratorsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
