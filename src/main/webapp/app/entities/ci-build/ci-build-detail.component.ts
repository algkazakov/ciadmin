import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICIBuild } from 'app/shared/model/ci-build.model';

@Component({
  selector: 'jhi-ci-build-detail',
  templateUrl: './ci-build-detail.component.html'
})
export class CIBuildDetailComponent implements OnInit {
  cIBuild: ICIBuild | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cIBuild }) => (this.cIBuild = cIBuild));
  }

  previousState(): void {
    window.history.back();
  }
}
