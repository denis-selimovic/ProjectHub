import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-issue-modal',
  templateUrl: './create-issue-modal.component.html',
  styleUrls: ['./create-issue-modal.component.scss']
})
export class CreateIssueModalComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
