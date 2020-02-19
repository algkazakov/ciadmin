import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICIBuild } from 'app/shared/model/ci-build.model';
import { CIBuildService } from './ci-build.service';

@Component({
  templateUrl: './ci-build-delete-dialog.component.html'
})
export class CIBuildDeleteDialogComponent {
  cIBuild?: ICIBuild;

  constructor(protected cIBuildService: CIBuildService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cIBuildService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cIBuildListModification');
      this.activeModal.close();
    });
  }
}
