import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CollaboratorService } from 'src/app/services/collaborator/collaborator.service';
import { Task, TaskService } from 'src/app/services/task/task.service';
import { User, UserService } from 'src/app/services/user/user.service';
import { TasksComponent } from '../tasks/tasks.component';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.scss']
})
export class TaskDetailsComponent implements OnInit {
  taskId: string;
  task: Task;
  projectId: string;
  collaborators: Array<User>;
  comments: any;
  priorities: any;
  statuses: any;
  types: any;
  leftForm: FormGroup;
  rightForm: FormGroup;
  errorMessage: String;
  currentUser: User;

  descriptionSuccessMessage: string;
  descriptionErrorMessage: string;
  userPriorityStatusSuccessMessage: string;
  userPriorityStatusErrorMessage: string;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService, private userService: UserService, private collaboratorService: CollaboratorService, private route: ActivatedRoute) { 
    this.descriptionErrorMessage = '';
    this.descriptionSuccessMessage = '';
    this.userPriorityStatusErrorMessage = '';
    this.userPriorityStatusSuccessMessage = '';
  }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();

    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.taskId = params.taskId;
    });
    
    this.loadTask();
 
    this.comments = [
      {
        id: "id",
        text: "This is current user's comment.",
        user: this.currentUser,
        task: this.task
      },
      {
        id: "id",
        text: "This is some other user's comment.",
        user: {
          id: "neki drugi id",
          firstName: "Lamija",
          lastName: "Vrnjak",
          email: "lvrnjak@gmail.com"
        },
        task: this.task
      }
    ] 

    this.errorMessage = "";
  }

  loadTask() {
    this.taskService.getTaskById(this.taskId,
      (data: any) => this.onTaskLoad(data),
      (err: any) => console.log(err));
  }

  onTaskLoad(data) {
    this.task = data;
    this.leftForm = this.formBuilder.group({
      description: new FormControl(this.task.description, [Validators.required, Validators.maxLength(255)]),
      comment: new FormControl('', [Validators.required, Validators.maxLength(255)])
    });   

    this.rightForm = this.formBuilder.group({
      collaborator: new FormControl(),
      priority: new FormControl(),
      status: new FormControl(),
      type: new FormControl()
    });   

    this.loadCollaborators(); 
    this.loadPriorities();
    this.loadStatuses();
    this.loadTypes();
  }

  loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.rightForm.patchValue({priority: this.priorities.find(p => p.id === this.task.priority.id)});
      }
    )
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        this.types = data.data;
        this.rightForm.patchValue({type: this.types.find(t => t.id === this.task.type.id)});
      } 
    )
  }

  loadStatuses() {
    this.taskService.getStatuses(
      (data: any) => {
        this.statuses = data.data;
        this.rightForm.patchValue({status: this.statuses.find(s => s.id === this.task.status.id)});
      }  
    )
  }

  loadCollaborators() {
    this.collaboratorService.getCollaborators(this.projectId,
      (data: any) => this.onCollaboratorsLoad(data), 
      () => this.userPriorityStatusErrorMessage = 'Error while loading data. Please try again later.');
  }

  onCollaboratorsLoad(data: any): any {
    this.collaborators = data;
    if (this.task.userId === null) 
      this.rightForm.patchValue({collaborator: null});
    else 
      this.rightForm.patchValue({collaborator: this.collaborators.find(c => c.id === this.task.userId)});
  }

  patchDescription(description: string) {
    this.taskService.patchTask(this.taskId, 
      {
        description: description
      }, 
      (data: any) => {
        this.descriptionSuccessMessage = "Description successfully changed";
        setTimeout(() => this.descriptionSuccessMessage = '', 1800);
      },
      (err: any) => { 
        this.descriptionErrorMessage = err.error.errors.message;
        setTimeout(() => this.descriptionErrorMessage = '', 1800);
      }
    );
  }

  addComment(comment: String) {
    this.comments.push({
        id: "id",
        text: comment,
        user: this.currentUser,
        task: this.task
    });
    this.leftForm.patchValue({comment: ''});
    console.log(comment);
  }

  patchUserPriorityStatus() {
    const form  = this.rightForm.getRawValue();
    this.taskService.patchTask(this.taskId, 
      {
        user_id: form.collaborator === null ? null : form.collaborator.id,
        priority_id: form.priority.id,
        status_id: form.status.id
      }, 
      (data: any) => {
        this.userPriorityStatusSuccessMessage = "Task details successfully changed";
        setTimeout(() => this.userPriorityStatusSuccessMessage = '', 1800);
      },
      (err: any) => { 
        this.userPriorityStatusErrorMessage = err.error.errors.message;
        setTimeout(() => this.userPriorityStatusErrorMessage = '', 1800);
      }
    );
  }

  subscribe() {
    console.log("subscribe");
  }
}
