import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-task-form',
  templateUrl: './new-task-form.component.html',
  styleUrls: ['./new-task-form.component.scss']
})

export class NewTaskFormComponent implements OnInit {
  newTaskForm: FormGroup;
  errorMessage: String;
  selectedPriority: any
  project: any;
  priorities: any;
  collaborators: any

  constructor(private formBuilder: FormBuilder) { 
    this.newTaskForm = this.formBuilder.group( {
      taskName: new FormControl('', [Validators.required, Validators.maxLength(50)]),      
      description: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      priority: new FormControl('', Validators.required),
      collaborator: new FormControl('')
    });
    this.errorMessage = ""
    this.project = {name: "NWT Project"}
    this.priorities = [{priority: "Critical"}, {priority: "High"}, {priority: "Medium"}, {priority: "Low"}]
    this.collaborators = [{email: "ajsa@gmail.com"}]
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }
}
