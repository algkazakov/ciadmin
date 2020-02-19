import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICIRuleGroup, CIRuleGroup } from 'app/shared/model/ci-rule-group.model';
import { CIRuleGroupService } from './ci-rule-group.service';

@Component({
  selector: 'jhi-ci-rule-group-update',
  templateUrl: './ci-rule-group-update.component.html'
})
export class CIRuleGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ruleGroupName: [],
    critical: [],
    type: []
  });

  constructor(protected cIRuleGroupService: CIRuleGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIRuleGroup }) => {
      this.updateForm(cIRuleGroup);
    });
  }

  updateForm(cIRuleGroup: ICIRuleGroup): void {
    this.editForm.patchValue({
      id: cIRuleGroup.id,
      ruleGroupName: cIRuleGroup.ruleGroupName,
      critical: cIRuleGroup.critical,
      type: cIRuleGroup.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cIRuleGroup = this.createFromForm();
    if (cIRuleGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.cIRuleGroupService.update(cIRuleGroup));
    } else {
      this.subscribeToSaveResponse(this.cIRuleGroupService.create(cIRuleGroup));
    }
  }

  private createFromForm(): ICIRuleGroup {
    return {
      ...new CIRuleGroup(),
      id: this.editForm.get(['id'])!.value,
      ruleGroupName: this.editForm.get(['ruleGroupName'])!.value,
      critical: this.editForm.get(['critical'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICIRuleGroup>>): void {
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
}
