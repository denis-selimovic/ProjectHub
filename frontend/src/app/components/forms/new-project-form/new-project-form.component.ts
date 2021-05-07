import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrls: ['./new-project-form.component.scss']
})
export class NewProjectFormComponent implements OnInit {
  newProjectForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) { 
    this.newProjectForm = this.formBuilder.group({
      projectName: new FormControl('', Validators.required)
    });
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
