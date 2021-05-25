import { Component, OnInit, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy, Output, EventEmitter} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Issue } from 'src/app/models/Issue';


@Component({
  selector: 'app-issue-details-form',
  templateUrl: './issue-details-form.component.html',
  styleUrls: ['./issue-details-form.component.scss']
})
export class IssueDetailsFormComponent implements OnChanges {
  issueDetailsForm: FormGroup;
  @Input() issue: Issue;
  show: boolean;
  priorities: Array<String>;
  reporters: any;
  @Output() closeEvent = new EventEmitter<boolean>();

  constructor(private formBuilder: FormBuilder) { 
    this.show = false;
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    //if(changes.hasOwnProperty('input'))
    if(this.issue !== undefined) {
      this.issueDetailsForm = this.formBuilder.group({
        issueName: new FormControl(this.issue.name, [Validators.required, Validators.maxLength(50)]),      
        description: new FormControl(this.issue.description, [Validators.required, Validators.maxLength(255)]),
        priority: new FormControl(this.issue.priority.toString, Validators.required),
        reporter: new FormControl('')
      });
      this.priorities = ["CRITICAL", "HIGH", "MEDIUM", "LOW"];
      this.reporters = [{email: "azigo1@etf.unsa.ba"}]
      this.show = true;
    }
  }

  onSubmit(): void {
  }

  close(value: boolean): void {
    this.issue = undefined;
    this.closeEvent.emit(value);
  }

}
