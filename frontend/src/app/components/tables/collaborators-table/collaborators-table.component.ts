import { Component, OnInit, AfterViewInit, ViewChild, Input } from '@angular/core';
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

  constructor(public dialog: MatDialog) {    
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<User>(this.collaborators);
  }
  
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }


  removeCollaborator(id: string): void {
    const dialogRef = this.dialog.open(ConfirmDeletionComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // this.collaborators = this.collaborators.filter(i => i.id !== id);
        this.dataSource.data = this.dataSource.data.filter(i => i.id !== id);
      }
    });
  }

}
