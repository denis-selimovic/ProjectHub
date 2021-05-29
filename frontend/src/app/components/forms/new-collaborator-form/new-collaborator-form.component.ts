import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { User, UserService } from 'src/app/services/user/user.service';
import { ActionResultComponent } from '../../dialogs/action-result/action-result.component';

@Component({
  selector: 'app-new-collaborator-form',
  templateUrl: './new-collaborator-form.component.html',
  styleUrls: ['./new-collaborator-form.component.scss']
})
export class NewCollaboratorFormComponent implements OnInit {
  newCollaboratorForm: FormGroup;
  @Output() onAddCollaborator = new EventEmitter<User>()

  constructor(private formBuilder: FormBuilder, private userService: UserService, private dialog: MatDialog) { 
    this.newCollaboratorForm = this.formBuilder.group( {
      email: new FormControl('', [Validators.email, Validators.required])
    })
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.userService.getUserByEmail(this.newCollaboratorForm.value.email, 
      (data: any) => this.onAddCollaborator.emit(data),
      (err: any) => {
        const dialogRef = this.dialog.open(ActionResultComponent);
        const instance = dialogRef.componentInstance;
        instance.message = err.error.errors.message;
        instance.title = "Error!";
      });
    this.newCollaboratorForm.reset();
  }

}
