import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiadminTestModule } from '../../../test.module';
import { CIBuildComponent } from 'app/entities/ci-build/ci-build.component';
import { CIBuildService } from 'app/entities/ci-build/ci-build.service';
import { CIBuild } from 'app/shared/model/ci-build.model';

describe('Component Tests', () => {
  describe('CIBuild Management Component', () => {
    let comp: CIBuildComponent;
    let fixture: ComponentFixture<CIBuildComponent>;
    let service: CIBuildService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIBuildComponent]
      })
        .overrideTemplate(CIBuildComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CIBuildComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CIBuildService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CIBuild(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cIBuilds && comp.cIBuilds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
