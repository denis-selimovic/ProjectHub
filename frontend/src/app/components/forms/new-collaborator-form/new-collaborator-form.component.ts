import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User, UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-new-collaborator-form',
  templateUrl: './new-collaborator-form.component.html',
  styleUrls: ['./new-collaborator-form.component.scss']
})
export class NewCollaboratorFormComponent implements OnInit {
  newCollaboratorForm: FormGroup;
  errorMessage: String
  @Output() onAddCollaborator = new EventEmitter<User>()

  constructor(private formBuilder: FormBuilder, private userService: UserService) { 
    this.newCollaboratorForm = this.formBuilder.group( {
      email: new FormControl('', [Validators.email, Validators.required])
    })
    this.errorMessage = "";
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.userService.getUserByEmail(this.newCollaboratorForm.value.email, 
      (data: any) => this.onAddCollaborator.emit(data),
      (err: any) => {console.log(err.error.errors.message)});
    this.newCollaboratorForm.reset();
  }

}
