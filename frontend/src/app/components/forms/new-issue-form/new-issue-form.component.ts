import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Project, ProjectService } from '../../../services/project/project.service';
import { Issue } from 'src/app/models/Issue';
import { IssueService } from 'src/app/services/issue/issue.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskService } from 'src/app/services/task/task.service';

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
  

  constructor(private formBuilder: FormBuilder, private issueService: IssueService, private projectService: ProjectService, private taskService: TaskService) {
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
    this.updateForm();
    setTimeout(() => this.createIssue(form), 1200);
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

  private updateForm(): void {
    this.newIssueForm = this.formBuilder.group( {
      name: new FormControl('', [Validators.required, Validators.maxLength(50)]),      
      description: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      priority_id: [this.priorities[0].id, Validators.required],
      project_id: new FormControl(this.projectId)
    });
  }

  private success(): void {
    this.successMessage = 'Successfully created an issue.';
    setTimeout(() => { this.successMessage = ''; }, 1800);
  }

  private error(): any {
    this.errorMessage = 'Something went wrong. Please try again.';
    setTimeout(() => { this.errorMessage = ''; }, 1800);
  }
}
