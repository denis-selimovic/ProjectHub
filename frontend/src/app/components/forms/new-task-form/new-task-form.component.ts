import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TaskService } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-new-task-form',
  templateUrl: './new-task-form.component.html',
  styleUrls: ['./new-task-form.component.scss']
})

export class NewTaskFormComponent implements OnInit {
  newTaskForm: FormGroup;
  errorMessage: String;
  project: any;
  priorities: any;
  types: any;
  collaborators: any;
  selectedPriority: any;
  selectedType: any;
  selectedCollaborator: any;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService) { 
    this.newTaskForm = this.formBuilder.group( {
      taskName: new FormControl('', [Validators.required, Validators.maxLength(50)]),      
      description: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      priority: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required),
      collaborator: new FormControl('')
    });
    this.errorMessage = ""
    this.project = {name: "NWT Project"}
    this.loadPriorities();
    this.loadTypes();
    this.collaborators = [{email: "ajsa@gmail.com"}]
    this.selectedCollaborator = this.collaborators[0];
  }

  
  loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.selectedPriority = this.priorities[0];
      }
    )
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        this.types = data.data;
        this.selectedType = this.types[0];
      } 
    )
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }
}
