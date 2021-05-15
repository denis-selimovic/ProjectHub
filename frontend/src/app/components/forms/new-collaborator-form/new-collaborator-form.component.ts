import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-collaborator-form',
  templateUrl: './new-collaborator-form.component.html',
  styleUrls: ['./new-collaborator-form.component.scss']
})
export class NewCollaboratorFormComponent implements OnInit {
  newCollaboratorForm: FormGroup;
  errorMessage: String

  constructor(private formBuilder: FormBuilder) { 
    this.newCollaboratorForm = this.formBuilder.group( {
      email: new FormControl('', [Validators.email, Validators.required])
    })
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
