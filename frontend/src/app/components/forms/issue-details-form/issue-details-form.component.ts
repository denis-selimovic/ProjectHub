import { getLocaleDateTimeFormat } from '@angular/common';
import { Component, OnInit, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy, Output, EventEmitter} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Issue } from 'src/app/models/Issue';
import { IssueService } from 'src/app/services/issue/issue.service';
import { TaskService } from 'src/app/services/task/task.service';


@Component({
  selector: 'app-issue-details-form',
  templateUrl: './issue-details-form.component.html',
  styleUrls: ['./issue-details-form.component.scss']
})
export class IssueDetailsFormComponent implements OnChanges {

  @Input() issue: Issue;
  @Output() closeEvent = new EventEmitter<boolean>();

  issueDetailsForm: FormGroup;
  errorMessage: string;
  successMessage: string;
  priorities: any;
  show: boolean;
  loader = false;
  
  constructor(private formBuilder: FormBuilder, private issueService: IssueService, private taskService: TaskService) { 
    this.taskService.getPriorities((data: any) => { this.priorities = data.data; console.log("prioriteti ", this.priorities);});
    this.show = false;
    this.successMessage = '';
    this.errorMessage = '';
  }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    if(this.issue !== undefined) {
      this.issueDetailsForm = this.formBuilder.group({
        name: new FormControl(this.issue.name, [Validators.required, Validators.maxLength(50)]),      
        description: new FormControl(this.issue.description, [Validators.required, Validators.maxLength(255)]),
        priority_id: new FormControl(this.issue.priority.id, Validators.required),
      });
      this.show = true;
    }
  }

  onSubmit(): void {
    const form = this.issueDetailsForm.getRawValue();
    this.loader = true;
    this.editIssue(form);
  }

  private editIssue(form: any): any {
    this.issueService.editIssue(this.issue.id, form,
      () => this.success(),
      (data: any) => this.error(data)
    );
  }

  close(value: boolean): void {
    this.closeEvent.emit(value);
  }

  success(): void {
    this.loader = false;
    this.successMessage = 'Issue successfully updated.';
    setTimeout(() => { 
      this.closeEvent.emit(true); 
      this.issue = undefined;
    }, 2000);
  }

  error(data: any) {
    this.loader = false;
    this.errorMessage = data.data;
  }

}
