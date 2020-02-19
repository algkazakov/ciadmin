import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIBuildUpdateComponent } from 'app/entities/ci-build/ci-build-update.component';
import { CIBuildService } from 'app/entities/ci-build/ci-build.service';
import { CIBuild } from 'app/shared/model/ci-build.model';

describe('Component Tests', () => {
  describe('CIBuild Management Update Component', () => {
    let comp: CIBuildUpdateComponent;
    let fixture: ComponentFixture<CIBuildUpdateComponent>;
    let service: CIBuildService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIBuildUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CIBuildUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CIBuildUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CIBuildService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CIBuild(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CIBuild();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
