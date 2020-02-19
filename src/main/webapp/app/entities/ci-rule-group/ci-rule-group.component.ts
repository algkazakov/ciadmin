import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICIRuleGroup } from 'app/shared/model/ci-rule-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CIRuleGroupService } from './ci-rule-group.service';
import { CIRuleGroupDeleteDialogComponent } from './ci-rule-group-delete-dialog.component';

@Component({
  selector: 'jhi-ci-rule-group',
  templateUrl: './ci-rule-group.component.html'
})
export class CIRuleGroupComponent implements OnInit, OnDestroy {
  cIRuleGroups: ICIRuleGroup[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected cIRuleGroupService: CIRuleGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.cIRuleGroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.cIRuleGroupService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICIRuleGroup[]>) => this.paginateCIRuleGroups(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.cIRuleGroups = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCIRuleGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICIRuleGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCIRuleGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('cIRuleGroupListModification', () => this.reset());
  }

  delete(cIRuleGroup: ICIRuleGroup): void {
    const modalRef = this.modalService.open(CIRuleGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cIRuleGroup = cIRuleGroup;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCIRuleGroups(data: ICIRuleGroup[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.cIRuleGroups.push(data[i]);
      }
    }
  }
}
