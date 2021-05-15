import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
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
  owner: User;
  collaborators: User[];
  dataSource: MatTableDataSource<User>
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public dialog: MatDialog) {    
    this.owner = {
      id: "12e08bf2-b4b2-4003-9288-507136ab459a",
      email: "ajsahaj@gmail.com",
      firstName: "Ajsa",
      lastName: "Hajradinovic"
    }

    this.collaborators = [
      {
        id: "13e08bf2-b4b2-4003-9288-507136ab459a",
        email: "lamijavrnjak@gmail.com",
        firstName: "Lamija",
        lastName: "Vrnjak"
      },
      {
        id: "14e08bf2-b4b2-4003-9288-507136ab459a",
        email: "amilazigo@gmail.com",
        firstName: "Amila",
        lastName: "Zigo"
      },
      {
        id: "15e08bf2-b4b2-4003-9288-507136ab459a",
        email: "denisselimovic@gmail.com",
        firstName: "Denis",
        lastName: "Selimovic"
      }
    ]
    this.dataSource = new MatTableDataSource<User>(this.collaborators);
  }

  ngOnInit(): void {
  }
  
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }


  removeCollaborator(id: string): void {
    const dialogRef = this.dialog.open(ConfirmDeletionComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // console.log(id)
        // const index = this.collaborators.findIndex(x => x.id === id)
        // this.collaborators.splice(index,);
        // this.dataSource = new MatTableDataSource<User>(this.collaborators);
        this.dataSource.data = this.dataSource.data.filter(i => i.id !== id)
      }
      console.log(`Dialog result: ${result}`);
    });
  }

}
