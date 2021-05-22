import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {

  @Input() message: any;
  @Input() errorMessage: any;
  @Input() successMessage: any;
  @Input() action: any;
  @Input() loader = false;

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit(): void { }

  confirm(): any {
    this.loader = true;
    setTimeout(() => this.doAction(), 1500);
  }

  cancel(): any {
    this.activeModal.close('error');
  }

  private doAction(): any {
    this.action();
    this.loader = false;
    setTimeout(() => this.close(), 1000);
  }

  private close(): any {
    this.activeModal.close('success');
  }
}
