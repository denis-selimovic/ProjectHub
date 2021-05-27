import { ChangeDetectorRef, Component, OnInit, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Task, TaskService } from 'src/app/services/task/task.service';
import { ActivatedRoute } from '@angular/router';
import { NewTaskFormComponent } from '../../forms/new-task-form/new-task-form.component';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
  tasks: Array<Task>;
  openTasks: Array<Task>;
  inProgressTasks: Array<Task>;
  inReviewTasks: Array<Task>;
  inTestingTasks: Array<Task>;
  doneTasks: Array<Task>;
  projectId: string;

  constructor(private modal: NgbModal, private taskService: TaskService, private route: ActivatedRoute, private changeDetection: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
    });
    this.loadTasks();
  }

  createTask() {
    const modal = this.modal.open(NewTaskFormComponent, { size: 'l' });
    modal.componentInstance.projectId = this.projectId;
    modal.result.then(result => {
      console.log("Rezultat ", result);
      if (result == 'success') {
        console.log("uspjesno dodan task");
        this.loadTasks();
        this.changeDetection.detectChanges();
      }
    }, err => {});
  }

  loadTasks() {
    this.taskService.getTasksByProjectId(this.projectId, 
      (data: any) => this.onTasksLoad(data),
      (err: any) => console.log(err));
  }

  onTasksLoad(data: any): void {
    this.tasks = data;
    this.openTasks = this.tasks.filter(task => task.status.status === "OPEN");
    this.inProgressTasks = this.tasks.filter(task => task.status.status === "IN_PROGRESS");
    this.inReviewTasks = this.tasks.filter(task => task.status.status === "IN_REVIEW");
    this.inTestingTasks = this.tasks.filter(task => task.status.status === "IN_TESTING");
    this.doneTasks = this.tasks.filter(task => task.status.status === "DONE");
  }
}
