import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICIRuleGroup } from 'app/shared/model/ci-rule-group.model';
import { CIRuleGroupService } from './ci-rule-group.service';

@Component({
  templateUrl: './ci-rule-group-delete-dialog.component.html'
})
export class CIRuleGroupDeleteDialogComponent {
  cIRuleGroup?: ICIRuleGroup;

  constructor(
    protected cIRuleGroupService: CIRuleGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cIRuleGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cIRuleGroupListModification');
      this.activeModal.close();
    });
  }
}
