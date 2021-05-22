import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-new-collaborator-form',
  templateUrl: './new-collaborator-form.component.html',
  styleUrls: ['./new-collaborator-form.component.scss']
})
export class NewCollaboratorFormComponent implements OnInit {
  newCollaboratorForm: FormGroup;
  errorMessage: String
  @Output() onAddCollaborator = new EventEmitter<User>()

  constructor(private formBuilder: FormBuilder) { 
    this.newCollaboratorForm = this.formBuilder.group( {
      email: new FormControl('', [Validators.email, Validators.required])
    })
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.onAddCollaborator.emit({
      id: "15e08bf2-b4kb2-4003-9288-507136ab459a",
      email:this.newCollaboratorForm.value.email,
      firstName: "New",
      lastName: "Collab"
    });
    this.newCollaboratorForm.reset();
  }

}
