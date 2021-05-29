import { Component, AfterViewInit, ViewChild, Input, EventEmitter, Output, SimpleChanges} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import { Collaborator, CollaboratorService } from 'src/app/services/collaborator/collaborator.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal/modal.component';

@Component({
  selector: 'app-collaborators-table',
  templateUrl: './collaborators-table.component.html',
  styleUrls: ['./collaborators-table.component.scss']
})

export class CollaboratorsTableComponent implements AfterViewInit {
  displayedColumns: string[] = ['name', 'collaboratorTag', 'email', 'deleteCollaborator'];
  @Input() collaborators: Array<Collaborator>;
  dataSource: MatTableDataSource<Collaborator>
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Output() onDelete: EventEmitter<any> = new EventEmitter();

  constructor(private dialog: MatDialog, private modal: NgbModal, private collaboratorService: CollaboratorService) {    
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<Collaborator>(this.collaborators);
  }
  
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(changes: SimpleChanges) {
    this.dataSource = new MatTableDataSource<Collaborator>(changes.collaborators.currentValue);
  }

  removeCollaborator(id: string, projectId: string): void {
    const deleteModal = this.modal.open(ModalComponent);
    deleteModal.componentInstance.message = 'Are you sure you want to remove this collaborator from the project?';
    deleteModal.componentInstance.successMessage = '';
    deleteModal.componentInstance.errorMessage = '';
    deleteModal.componentInstance.action = () => {
      this.collaboratorService.deleteCollaborator(projectId, 
      id,
      (data: any) => {
        deleteModal.componentInstance.successMessage = 'Collaborator successfully removed.';
      },
      (err: any ) => {
        deleteModal.componentInstance.errorMessage = 'Something went wrong when deleting project. Please try again.';      
      });
    }

    deleteModal.result.then(result => {
      if (result === 'success') {
        this.onDelete.emit();
      }
    }).catch(err => {});
  }

}
