import { Component, OnInit } from '@angular/core';
import { Task } from 'src/app/models/Task';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NewTaskModalComponent } from '../../dialogs/new-task-modal/new-task-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateTaskModalComponent } from '../../modals/create-task-modal/create-task-modal.component';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
  tasks: Array<Task>
  openTasks: Array<Task>
  inProgressTasks: Array<Task>
  inReviewTasks: Array<Task>
  inTestingTasks: Array<Task>
  doneTasks: Array<Task>

  constructor(public matDialog: MatDialog, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.tasks = [
      {
        id: "12e08bf2-b4b2-4003-9288-507136ab459a",
        name: "Install Ruby nstall Ruby ",
        description: "Description",
        userName: "Lamija Vrnjak",
        projectName: "NWT-101",
        status: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          status: "IN PROGRESS",
        },
        type: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          type: "CRITICAL"
        }
      },
      {
        id: "12e08bf2-b4b2-4003-9288-507136ab459a",
        name: "Install Ruby",
        description: "Description",
        userName: "Lamija Vrnjak",
        projectName: "NWT-101",
        status: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          status: "OPEN",
        },
        type: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          type: "CRITICAL"
        }
      },
      {
        id: "12e08bf2-b4b2-4003-9288-507136ab459a",
        name: "Install Ruby",
        description: "Description",
        userName: "Lamija Vrnjak",
        projectName: "NWT-101",
        status: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          status: "IN PROGRESS",
        },
        type: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          type: "CRITICAL"
        }
      },
      {
        id: "12e08bf2-b4b2-4003-9288-507136ab459a",
        name: "Install Ruby",
        description: "Description",
        userName: "Lamija Vrnjak",
        projectName: "NWT-101",
        status: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          status: "IN REVIEW",
        },
        type: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          type: "LOW"
        }
      },
      {
        id: "12e08bf2-b4b2-4003-9288-507136ab459a",
        name: "Install Ruby",
        description: "Description",
        userName: "Lamija Vrnjak",
        projectName: "NWT-101",
        status: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          status: "DONE",
        },
        type: {
          id: "12e08bf2-b4b2-4003-9288-507136ab459a",
          type: "MEDIUM"
        }
      },
    ]
    this.openTasks = this.tasks.filter(task => task.status.status === "OPEN");
    this.inProgressTasks = this.tasks.filter(task => task.status.status === "IN PROGRESS");
    this.inReviewTasks = this.tasks.filter(task => task.status.status === "IN REVIEW");
    this.inTestingTasks = this.tasks.filter(task => task.status.status === "IN TESTING");
    this.doneTasks = this.tasks.filter(task => task.status.status === "DONE");
  }

  openModal() {
    const modalRef = this.modalService.open(CreateTaskModalComponent);
    // const dialogConfig = new MatDialogConfig();
    // dialogConfig.disableClose = true;
    // this.matDialog.open(NewTaskModalComponent, dialogConfig);
  }
}
