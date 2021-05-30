import { Component, Input, OnInit, Output } from '@angular/core';
import { Issue } from 'src/app/models/Issue';
import { EventEmitter } from '@angular/core'; 
import { TaskService, Type } from 'src/app/services/task/task.service';

@Component({
  selector: 'app-issue-item',
  templateUrl: './issue-item.component.html',
  styleUrls: ['./issue-item.component.scss']
})
export class IssueItemComponent implements OnInit {

  @Input() issue: Issue;
  @Output() itemEvent = new EventEmitter<Issue>();
  @Output() deleteIssueEvent = new EventEmitter<string>();

  imageSrc: string;
  bugType: Type;
  errorMessage: string;
  successMessage: string;

  constructor(private taskService: TaskService) {
    this.successMessage = '';
    this.errorMessage = '';
  }

  ngOnInit(): void {
    this.loadTypes();

    switch (this.issue.priority.priority_type) {
      case "CRITICAL":
        this.imageSrc = "assets/critical.png";
        break;
      case "HIGH":
        this.imageSrc = "assets/high.png";
        break;
      case "MEDIUM":
        this.imageSrc = "assets/medium.png";
        break;
      case "LOW":
        this.imageSrc = "assets/low.png";
        break;
      default:
        this.imageSrc = "assets/undefined.png";
        break;
    }
  }

  createTaskFromIssue(issue: any) {
    const request = {
      name: issue.name,
      description: issue.description,
      project_id: issue.project_id,
      priority_id: issue.priority.id,
      typeId: this.bugType.id
    }
    this.taskService.createTask(request, 
      () => this.success(),
      (err: any) => this.error(err)
    )
  }

  detailsClicked(issue: Issue) {
    this.itemEvent.emit(issue);
  }

  removeIssue(id: string) {
    this.deleteIssueEvent.emit(id);
  }

  loadTypes() {
    this.taskService.getTypes(
      (data: any) => {
        const types = data.data;
        this.bugType = types.find(t => t.type === "BUG");
      } 
    )
  }

  private success(): void {
    this.successMessage = 'Successfully created a task.';
    setTimeout(() => { this.successMessage = ''; }, 1800);
  }

  private error(err): any {
    this.errorMessage = err.error.errors.message;
    // this.errorMessage = 'Something went wrong. Please try again.';
    setTimeout(() => { this.errorMessage = ''; }, 1800);
  }
}
