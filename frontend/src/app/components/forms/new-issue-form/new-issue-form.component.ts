import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-issue-form',
  templateUrl: './new-issue-form.component.html',
  styleUrls: ['./new-issue-form.component.scss']
})
export class NewIssueFormComponent implements OnInit {
  newIssueForm: FormGroup;
  errorMessage: String;
  selectedPriority: any;
  project: any;
  priorities: any;
  collaborators: any;

  constructor(private formBuilder: FormBuilder) {
    this.newIssueForm = this.formBuilder.group( {
      issueName: new FormControl('', Validators.required),      
      description: new FormControl('', Validators.required),
      priority: new FormControl('', Validators.required)
    });
    this.errorMessage = ""
    this.project = {name: "NWT Project"}
    this.priorities = [{priority: "High"}, {priority: "Medium"}]
    this.collaborators = [{email: "ajsa@gmail.com"}]
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }
}
