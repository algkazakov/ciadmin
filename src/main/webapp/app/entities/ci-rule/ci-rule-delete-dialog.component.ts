import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICIRule } from 'app/shared/model/ci-rule.model';
import { CIRuleService } from './ci-rule.service';

@Component({
  templateUrl: './ci-rule-delete-dialog.component.html'
})
export class CIRuleDeleteDialogComponent {
  cIRule?: ICIRule;

  constructor(protected cIRuleService: CIRuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cIRuleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cIRuleListModification');
      this.activeModal.close();
    });
  }
}
