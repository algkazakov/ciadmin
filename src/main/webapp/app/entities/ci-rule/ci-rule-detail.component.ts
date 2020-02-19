import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICIRule } from 'app/shared/model/ci-rule.model';

@Component({
  selector: 'jhi-ci-rule-detail',
  templateUrl: './ci-rule-detail.component.html'
})
export class CIRuleDetailComponent implements OnInit {
  cIRule: ICIRule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIRule }) => (this.cIRule = cIRule));
  }

  previousState(): void {
    window.history.back();
  }
}
