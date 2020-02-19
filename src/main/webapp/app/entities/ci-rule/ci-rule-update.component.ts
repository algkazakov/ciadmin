import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICIRule, CIRule } from 'app/shared/model/ci-rule.model';
import { CIRuleService } from './ci-rule.service';
import { ICIRuleGroup } from 'app/shared/model/ci-rule-group.model';
import { CIRuleGroupService } from 'app/entities/ci-rule-group/ci-rule-group.service';

@Component({
  selector: 'jhi-ci-rule-update',
  templateUrl: './ci-rule-update.component.html'
})
export class CIRuleUpdateComponent implements OnInit {
  isSaving = false;
  cirulegroups: ICIRuleGroup[] = [];

  editForm = this.fb.group({
    id: [],
    ruleName: [],
    critical: [],
    useInReport: [],
    useInMicroservice: [],
    modifyDate: [],
    cIRuleGroupId: []
  });

  constructor(
    protected cIRuleService: CIRuleService,
    protected cIRuleGroupService: CIRuleGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIRule }) => {
      if (!cIRule.id) {
        const today = moment().startOf('day');
        cIRule.modifyDate = today;
      }

      this.updateForm(cIRule);

      this.cIRuleGroupService.query().subscribe((res: HttpResponse<ICIRuleGroup[]>) => (this.cirulegroups = res.body || []));
    });
  }

  updateForm(cIRule: ICIRule): void {
    this.editForm.patchValue({
      id: cIRule.id,
      ruleName: cIRule.ruleName,
      critical: cIRule.critical,
      useInReport: cIRule.useInReport,
      useInMicroservice: cIRule.useInMicroservice,
      modifyDate: cIRule.modifyDate ? cIRule.modifyDate.format(DATE_TIME_FORMAT) : null,
      cIRuleGroupId: cIRule.cIRuleGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cIRule = this.createFromForm();
    if (cIRule.id !== undefined) {
      this.subscribeToSaveResponse(this.cIRuleService.update(cIRule));
    } else {
      this.subscribeToSaveResponse(this.cIRuleService.create(cIRule));
    }
  }

  private createFromForm(): ICIRule {
    return {
      ...new CIRule(),
      id: this.editForm.get(['id'])!.value,
      ruleName: this.editForm.get(['ruleName'])!.value,
      critical: this.editForm.get(['critical'])!.value,
      useInReport: this.editForm.get(['useInReport'])!.value,
      useInMicroservice: this.editForm.get(['useInMicroservice'])!.value,
      modifyDate: this.editForm.get(['modifyDate'])!.value ? moment(this.editForm.get(['modifyDate'])!.value, DATE_TIME_FORMAT) : undefined,
      cIRuleGroupId: this.editForm.get(['cIRuleGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICIRule>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICIRuleGroup): any {
    return item.id;
  }
}
