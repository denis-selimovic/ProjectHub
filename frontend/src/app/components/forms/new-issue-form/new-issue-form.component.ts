import { Component, OnInit, Input, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProjectService } from '../../../services/project/project.service';
import { IssueService } from 'src/app/services/issue/issue.service';
import { TaskService } from 'src/app/services/task/task.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-new-issue-form',
  templateUrl: './new-issue-form.component.html',
  styleUrls: ['./new-issue-form.component.scss']
})
export class NewIssueFormComponent implements OnInit {

  @Input() projectId: string;

  newIssueForm: FormGroup;
  errorMessage:string;
  successMessage: string;
  projectName: string;
  priorities: any;
  loader = false;
  
  constructor(private formBuilder: FormBuilder, private issueService: IssueService, private projectService: ProjectService, private taskService: TaskService, public activeModal: NgbActiveModal) {
    this.loadPriorities();
    this.successMessage = '';
    this.errorMessage = '';
  }

  ngOnInit(): void {
    this.projectService.getProjectById(this.projectId, (data: any) => {
      this.projectName = data.name;
    }, (err: any) => {});
  }

  onSubmit(): void {
    const form = this.newIssueForm.getRawValue();
    this.loader = true;
    this.updateForm();
    this.createIssue(form);
  }

  private createIssue(form: any): any {
    this.issueService.createIssue(form,
      () => this.success(),
      () => this.error()
    );
  }

  private loadPriorities() {
    this.taskService.getPriorities(
      (data: any) => {
        this.priorities = data.data;
        this.updateForm();
      }
    )
  }

  updateForm(): FormGroup {
    this.newIssueForm = this.formBuilder.group( {
      name: new FormControl('', [Validators.required, Validators.maxLength(50)]),      
      description: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      priority_id: [this.priorities[0].id, Validators.required],
      project_id: new FormControl(this.projectId)
    });
    return this.newIssueForm;
  }

  private success(): void {
    this.loader = false;
    this.successMessage = 'Successfully created an issue.';
    setTimeout(() => { 
      this.activeModal.close(); 
  }, 1800);
  }

  private error(): any {
    this.loader = false;
    this.errorMessage = 'Something went wrong. Please try again.';
    setTimeout(() => { 
      this.activeModal.close();
    }, 1800);
  }
}
