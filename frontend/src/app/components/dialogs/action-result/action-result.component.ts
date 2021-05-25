import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-action-result',
  templateUrl: './action-result.component.html',
  styleUrls: ['./action-result.component.scss']
})
export class ActionResultComponent implements OnInit {
  @Input() message: string;
  @Input() title: string;

  constructor() { }

  ngOnInit(): void {
  }

}
