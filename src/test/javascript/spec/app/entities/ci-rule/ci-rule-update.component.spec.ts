import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIRuleUpdateComponent } from 'app/entities/ci-rule/ci-rule-update.component';
import { CIRuleService } from 'app/entities/ci-rule/ci-rule.service';
import { CIRule } from 'app/shared/model/ci-rule.model';

describe('Component Tests', () => {
  describe('CIRule Management Update Component', () => {
    let comp: CIRuleUpdateComponent;
    let fixture: ComponentFixture<CIRuleUpdateComponent>;
    let service: CIRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIRuleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CIRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CIRuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CIRuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CIRule(123);
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
        const entity = new CIRule();
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
