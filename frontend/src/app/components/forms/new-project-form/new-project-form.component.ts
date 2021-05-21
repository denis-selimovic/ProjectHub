import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {ProjectService} from '../../../services/project/project.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrls: ['./new-project-form.component.scss']
})
export class NewProjectFormComponent implements OnInit {
  newProjectForm: FormGroup;
  errorMessage: string;
  successMessage: string;
  loader = false;

  constructor(private formBuilder: FormBuilder, private projectService: ProjectService,
              public activeModal: NgbActiveModal) {
    this.newProjectForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required, Validators.maxLength(50)])
    });
    this.errorMessage = '';
    this.successMessage = '';
  }

  ngOnInit(): void {
    this.newProjectForm.valueChanges.subscribe(() => {
      this.errorMessage = '';
      this.successMessage = '';
    });
  }

  onSubmit(): void {
    const form = this.newProjectForm.getRawValue();
    this.newProjectForm.reset();
    this.loader = true;
    setTimeout(() => this.createProject(form), 1200);
  }

  private success(data: any): any {
    this.loader = false;
    this.successMessage = 'Successfully created a project. Go ahead and invite your colleagues.';
    setTimeout(() => this.close('success'), 2000);
  }

  private error(res: any): any {
    this.loader = false;
    if (res?.error?.errors?.message[0]) {
      this.errorMessage = res.error.errors.message[0];
    }
    else {
      this.errorMessage = 'Something went wrong. Please try again';
    }
    setTimeout(() => this.close('error'), 2000);
  }

  private createProject(form: any): any {
    this.projectService.createProject(form,
      (data: any) => this.success(data),
      (err: any) => this.error(err)
    );
  }

  private close(status: string): any {
    this.activeModal.close(status);
  }
}
