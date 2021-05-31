import { Component, AfterViewInit, ViewChild, Input, EventEmitter, Output, SimpleChanges} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import { User } from 'src/app/models/User';
import { ConfirmDeletionComponent } from '../../dialogs/confirm-deletion/confirm-deletion.component';

@Component({
  selector: 'app-collaborators-table',
  templateUrl: './collaborators-table.component.html',
  styleUrls: ['./collaborators-table.component.scss']
})

export class CollaboratorsTableComponent implements AfterViewInit {
  displayedColumns: string[] = ['name', 'collaboratorTag', 'email', 'deleteCollaborator'];
  @Input() collaborators: Array<User>;
  dataSource: MatTableDataSource<User>
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Output() onDelete: EventEmitter<any> = new EventEmitter();

  constructor(private dialog: MatDialog) {    
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<User>(this.collaborators);
  }
  
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(changes: SimpleChanges) {
    this.dataSource = new MatTableDataSource<User>(changes.collaborators.currentValue);
  }

  removeCollaborator(id: string): void {
    const dialogRef = this.dialog.open(ConfirmDeletionComponent);
    const instance = dialogRef.componentInstance;
    instance.message = "Do you want to remove this collaborator from the project?";

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.onDelete.emit(id);
      }
    });
  }

}
