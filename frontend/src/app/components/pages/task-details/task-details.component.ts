import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Task } from 'src/app/models/Task';
import { TaskService } from 'src/app/services/task/task.service';
import { User, UserService } from 'src/app/services/user/user.service';
import { TasksComponent } from '../tasks/tasks.component';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.scss']
})
export class TaskDetailsComponent implements OnInit {
  task: Task;
  project: any;
  collaborators: Array<User>;
  comments: any;
  priorities: any;
  statuses: any;
  types: any;
  leftForm: FormGroup;
  rightForm: FormGroup;
  errorMessage: String;
  currentUser: User;
  selectedCollaborator: User;
  selectedPriority: any;
  selectedStatus: any;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService, private userService: UserService) { 
  }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    this.loadPriorities();
    this.loadStatuses();
    this.loadTypes();

    this.project = {
      name: "NWT-101"
    }

    this.task = 
    {
      id: "12e08bf2-b4b2-4003-9288-507136ab459a",
      name: "Create design",
      description: "This task includes creating UI design for each functionality of the application. Design should follow best practices.",
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
    }

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

    this.selectedCollaborator = this.collaborators[0];

    this.errorMessage = "";

    this.leftForm = this.formBuilder.group({
      description: new FormControl(this.task.description, [Validators.required, Validators.maxLength(255)]),
      comment: new FormControl('', [Validators.required, Validators.maxLength(255)])
    });   

    this.rightForm = this.formBuilder.group({
      collaborator: new FormControl(),
      priority: new FormControl(),
      status: new FormControl()
    });   
  }

  loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.selectedPriority = this.priorities[0];
        console.log(data.data);
      }
    )
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        this.types = data.data     
      } 
    )
  }

  loadStatuses() {
    this.taskService.getStatuses(
      (data: any) => {
        this.statuses = data.data;
        this.selectedStatus = this.statuses[0];
      }  
    )
  }

  patchDescription(description: String) {
    console.log(description);
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

  patch2() {
    console.log("desno");
  }

  subscribe() {
    console.log("subscribe");
  }
}
