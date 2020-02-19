import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICIBuild } from 'app/shared/model/ci-build.model';
import { CIBuildService } from './ci-build.service';
import { CIBuildDeleteDialogComponent } from './ci-build-delete-dialog.component';

@Component({
  selector: 'jhi-ci-build',
  templateUrl: './ci-build.component.html'
})
export class CIBuildComponent implements OnInit, OnDestroy {
  cIBuilds?: ICIBuild[];
  eventSubscriber?: Subscription;

  constructor(protected cIBuildService: CIBuildService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.cIBuildService.query().subscribe((res: HttpResponse<ICIBuild[]>) => (this.cIBuilds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCIBuilds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICIBuild): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCIBuilds(): void {
    this.eventSubscriber = this.eventManager.subscribe('cIBuildListModification', () => this.loadAll());
  }

  delete(cIBuild: ICIBuild): void {
    const modalRef = this.modalService.open(CIBuildDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cIBuild = cIBuild;
  }
}
