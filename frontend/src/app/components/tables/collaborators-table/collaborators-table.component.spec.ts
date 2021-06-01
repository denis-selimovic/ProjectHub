import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTableDataSource } from '@angular/material/table';

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

  it('should be able to show a message when no data is being displayed', () => { 
    component.dataSource = new MatTableDataSource<any>([]);
    fixture.detectChanges();
    const noDataField = fixture.debugElement.nativeElement.querySelector('.no-records');
    expect(noDataField).toBeTruthy();
  })
});
