import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { CollaboratorsTableComponent } from './collaborators-table.component';

describe('CollaboratorsTableComponent', () => {
  let component: CollaboratorsTableComponent;
  let fixture: ComponentFixture<CollaboratorsTableComponent>;
  let table: HTMLElement;

  CommonTestingModule.setUpTestBed(CollaboratorsTableComponent);

  beforeEach(() => {
    fixture = TestBed.createComponent(CollaboratorsTableComponent);
    table = fixture.debugElement.nativeElement.querySelector('table');
    component = fixture.componentInstance;
    component.collaborators = [
      {
        id: "id1",
        firstName: "Ime",
        lastName: "Prezime",
        email: "imeprezime@projecthub.com"
      },
      {
        id: "id2",
        firstName: "Name",
        lastName: "Last name",
        email: "username@projecthub.com"
      }      
    ]; 
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render rows with right number of row cells', () => {
    const rows = CommonTestingModule.getRows(fixture.debugElement.nativeElement);

    expect(rows.length).toBe(component.collaborators.length);
    rows.forEach(row => {
      expect(CommonTestingModule.getCells(row).length).toBe(component.displayedColumns.length);
    });
  });
});
