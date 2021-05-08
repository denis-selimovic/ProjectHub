import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, Form, FormsModule } from '@angular/forms';
import { FieldsMatch } from 'src/app/validators/fieldsMatch.validator';
import { MatInputModule} from '@angular/material/input';

@Component({
  selector: 'app-reset-pass-new-form',
  templateUrl: './reset-pass-new-form.component.html',
  styleUrls: ['./reset-pass-new-form.component.scss']
})

export class ResetPassNewFormComponent implements OnInit {
  resetPassNewForm: FormGroup;
  errorMessage: String;
  hide: boolean;
  hideConf: boolean

  constructor(private formBuilder: FormBuilder) {
    this.errorMessage = ""; //error message from server
    this.hide = true;
    this.hideConf = true;
    this.resetPassNewForm = this.formBuilder.group({
    password: new FormControl('',[Validators.required, 
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}$'),
      Validators.minLength(8)]),
    confirmPassword: new FormControl('', [Validators.required])
  }, {
    validators: FieldsMatch('password', 'confirmPassword')
    });
  }

  ngOnInit(): void {
  }

}

