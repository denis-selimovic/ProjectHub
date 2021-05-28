import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Project, ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-create-task-modal',
  templateUrl: './create-task-modal.component.html',
  styleUrls: ['./create-task-modal.component.scss']
})
export class CreateTaskModalComponent implements OnInit {

  @Input() projectId: string;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
