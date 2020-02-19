import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIBuildDetailComponent } from 'app/entities/ci-build/ci-build-detail.component';
import { CIBuild } from 'app/shared/model/ci-build.model';

describe('Component Tests', () => {
  describe('CIBuild Management Detail Component', () => {
    let comp: CIBuildDetailComponent;
    let fixture: ComponentFixture<CIBuildDetailComponent>;
    const route = ({ data: of({ cIBuild: new CIBuild(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIBuildDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CIBuildDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CIBuildDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cIBuild on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cIBuild).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
