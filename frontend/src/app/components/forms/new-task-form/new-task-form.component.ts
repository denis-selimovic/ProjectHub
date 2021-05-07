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
      taskName: new FormControl('', Validators.required),      
      description: new FormControl('', Validators.required)
    });
    this.errorMessage = ""
    this.project = {name: "NWT Project"}
    this.priorities = [{priority: "High"}, {priority: "Medium"}]
    this.collaborators = [{email: "ajsa@gmail.com"}]
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }
}
