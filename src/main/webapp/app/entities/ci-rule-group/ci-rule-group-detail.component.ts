import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICIRuleGroup } from 'app/shared/model/ci-rule-group.model';

@Component({
  selector: 'jhi-ci-rule-group-detail',
  templateUrl: './ci-rule-group-detail.component.html'
})
export class CIRuleGroupDetailComponent implements OnInit {
  cIRuleGroup: ICIRuleGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIRuleGroup }) => (this.cIRuleGroup = cIRuleGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
