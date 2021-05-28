import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CollaboratorService } from 'src/app/services/collaborator/collaborator.service';
import { ProjectService } from 'src/app/services/project/project.service';
import { TaskService } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-new-task-form',
  templateUrl: './new-task-form.component.html',
  styleUrls: ['./new-task-form.component.scss']
})

export class NewTaskFormComponent implements OnInit {
  newTaskForm: FormGroup;
  errorMessage: string;
  successMessage: string;
  project: any;
  priorities: any;
  types: any;
  collaborators: any;
  priority: any;
  type: any;
  collaborator: any;
  @Input() projectId: string;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService, private collaboratorService: CollaboratorService, private projectService: ProjectService, public activeModal: NgbActiveModal) { 
    this.newTaskForm = this.formBuilder.group( {
      taskName: new FormControl('', [Validators.required, Validators.maxLength(50)]),      
      description: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      priority: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required),
      collaborator: new FormControl('', Validators.required)
    });
    this.errorMessage = '';
    this.successMessage = '';
  }

  ngOnInit(): void {
    this.newTaskForm.valueChanges.subscribe(() => {
      this.errorMessage = '';
      this.successMessage = '';
    });
    this.loadProject();
    this.loadPriorities();
    this.loadTypes();
    this.loadCollaborators();
  }
  
  loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.priority = this.priorities[0];
      }
    )
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        this.types = data.data;
        this.type = this.types[0];
      } 
    )
  }

  loadProject() {
    this.projectService.getProjectById(this.projectId, 
      (data: any) => this.onProjectLoad(data),
      (err: any) => this.errorMessage = err);
  }

  onProjectLoad(data) {
    this.project = data;   
  }

  loadCollaborators() {
    this.collaboratorService.getCollaborators(this.projectId,
      (data: any) => this.onCollaboratorsLoad(data), 
      () => this.errorMessage = 'Error while loading data. Please try again later.');
  }

  onCollaboratorsLoad(data: any): any {
    this.collaborators = data;
    this.collaborator = null;
  }

  onSubmit(): void {
    const form = this.newTaskForm.getRawValue();
    const requestBody = {
      name: form.taskName,
      userId: form.collaborator.id,
      description: form.description,
      projectId: this.projectId,
      priorityId: form.priority.id,
      typeId: form.type.id
    };
    this.newTaskForm.reset();
    this.taskService.createTask(requestBody, 
      (data: any) => this.success(data),
      (err: any) => this.error(err));
  }

  private success(data: any): any {
    this.successMessage = 'Successfully created a task.';
    setTimeout(() => this.close('success'), 2000);
  }

  private error(res: any): any {
    if (res?.error?.errors?.message[0]) {
      this.errorMessage = res.error.errors.message[0];
    }
    else {
      this.errorMessage = 'Something went wrong. Please try again';
    }
    setTimeout(() => this.close('error'), 2000);
  }

  private close(status: string): any {
    this.activeModal.close(status);
  }

}
