import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Task, TaskService } from 'src/app/services/task/task.service';
import { ModalComponent } from '../../modal/modal/modal.component';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent implements OnInit {

  @Input() tasks: Array<Task> = [];
  @Output() onTaskDelete: EventEmitter<any> = new EventEmitter<any>();

  constructor(private modal: NgbModal, private taskService: TaskService) { }

  ngOnInit(): void {
  }

  deleteTask(taskId: string) {
    const deleteModal = this.modal.open(ModalComponent);
    deleteModal.componentInstance.message = 'Are you sure you want to delete this task?';
    deleteModal.componentInstance.successMessage = '';
    deleteModal.componentInstance.errorMessage = '';
    deleteModal.componentInstance.action = () => {
      this.taskService.deleteTask(taskId, 
        (data: any) => {
          deleteModal.componentInstance.successMessage = 'Task successfully deleted.';
        }, 
        (err: any) => {
          deleteModal.componentInstance.errorMessage = err.error.errors.message;
        });
    };
    deleteModal.result.then(result => {
      if (result === 'success') {
        this.onTaskDelete.emit();
      }
    }).catch(err => {});
  }
}
