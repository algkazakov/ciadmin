import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICIBuild, CIBuild } from 'app/shared/model/ci-build.model';
import { CIBuildService } from './ci-build.service';

@Component({
  selector: 'jhi-ci-build-update',
  templateUrl: './ci-build-update.component.html'
})
export class CIBuildUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    moduleName: [],
    moduleVersion: [],
    branch: [],
    ciResult: [],
    sonarResult: [],
    buildDate: [],
    sonarProject: []
  });

  constructor(protected cIBuildService: CIBuildService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIBuild }) => {
      if (!cIBuild.id) {
        const today = moment().startOf('day');
        cIBuild.buildDate = today;
      }

      this.updateForm(cIBuild);
    });
  }

  updateForm(cIBuild: ICIBuild): void {
    this.editForm.patchValue({
      id: cIBuild.id,
      moduleName: cIBuild.moduleName,
      moduleVersion: cIBuild.moduleVersion,
      branch: cIBuild.branch,
      ciResult: cIBuild.ciResult,
      sonarResult: cIBuild.sonarResult,
      buildDate: cIBuild.buildDate ? cIBuild.buildDate.format(DATE_TIME_FORMAT) : null,
      sonarProject: cIBuild.sonarProject
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cIBuild = this.createFromForm();
    if (cIBuild.id !== undefined) {
      this.subscribeToSaveResponse(this.cIBuildService.update(cIBuild));
    } else {
      this.subscribeToSaveResponse(this.cIBuildService.create(cIBuild));
    }
  }

  private createFromForm(): ICIBuild {
    return {
      ...new CIBuild(),
      id: this.editForm.get(['id'])!.value,
      moduleName: this.editForm.get(['moduleName'])!.value,
      moduleVersion: this.editForm.get(['moduleVersion'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      ciResult: this.editForm.get(['ciResult'])!.value,
      sonarResult: this.editForm.get(['sonarResult'])!.value,
      buildDate: this.editForm.get(['buildDate'])!.value ? moment(this.editForm.get(['buildDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sonarProject: this.editForm.get(['sonarProject'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICIBuild>>): void {
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
