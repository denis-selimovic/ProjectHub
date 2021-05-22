import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalComponent} from '../../modal/modal/modal.component';
import {ProjectService} from '../../../services/project/project.service';

@Component({
  selector: 'app-projects-table',
  templateUrl: './projects-table.component.html',
  styleUrls: ['./projects-table.component.scss']
})
export class ProjectsTableComponent implements OnInit {

  @Input() projects: any = [];
  @Output() projectDelete: EventEmitter<any> = new EventEmitter<any>();

  constructor(private modal: NgbModal, private projectService: ProjectService) { }

  ngOnInit(): void { }

  deleteProject($event: any, projectId: string): any {
    const deleteModal = this.modal.open(ModalComponent);
    deleteModal.componentInstance.message = 'Are you sure you want to delete this project?';
    deleteModal.componentInstance.successMessage = '';
    deleteModal.componentInstance.errorMessage = '';
    deleteModal.componentInstance.action = () => {
      this.projectService.deleteProject(projectId, (data: any) => {
        deleteModal.componentInstance.successMessage = 'Project successfully deleted.';
      }, (err: any) => {
        deleteModal.componentInstance.errorMessage = 'Something went wrong when deleting project. Please try again.';
      });
    };
    deleteModal.result.then(result => {
      if (result === 'success') {
        this.projectDelete.emit();
      }
    }).catch(err => {});
  }
}
