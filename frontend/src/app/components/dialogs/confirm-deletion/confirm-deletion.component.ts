import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-confirm-deletion',
  templateUrl: './confirm-deletion.component.html',
  styleUrls: ['./confirm-deletion.component.scss']
})
export class ConfirmDeletionComponent implements OnInit {
  @Input() message: String;

  constructor() { }

  ngOnInit(): void {
  }

}
