import { PageEvent } from '@angular/material/paginator';
import { Project, ProjectService } from '../../../services/project/project.service';
import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NewProjectComponent } from '../new-project/new-project.component';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {

  @Input() type: string;
  @Input() title: string;

  projectNum = 0;
  page = 0;
  size = 5;
  projects: Array<Project> = [];
  pageOptions = [5, 10, 20];

  constructor(private projectService: ProjectService, private modal: NgbModal) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  paginate($event: PageEvent): any {
    if (this.page === $event.pageIndex && this.size === $event.pageSize ) {
      return;
    }
    this.page = $event.pageIndex;
    this.size = $event.pageSize;
    this.loadProjects();
  }

  onProjectsLoad(data: any): any {
    this.projects = data.data;
    this.projectNum = this.projects.length;
    if (this.projectNum === 0) {
      this.title = (this.type === 'owner') ? 'You don\'t own any projects. Create new project here' : 'You don\'t collaborate on any project';
    }
  }

  loadProjects(): any {
    this.projectService.getProjects(this.type, this.page, this.size,
      (data: any) => this.onProjectsLoad(data),
      () => this.title = 'Error while loading data. Please try again later.'
    );
  }

  createProject(): any {
    const modal = this.modal.open(NewProjectComponent, { size: 'xl' });
    modal.result.then(result => {
      if (result === 'success') {
        this.loadProjects();
      }
    }, err => {});
  }

  onProjectDelete(): any {
    this.loadProjects();
  }
}
