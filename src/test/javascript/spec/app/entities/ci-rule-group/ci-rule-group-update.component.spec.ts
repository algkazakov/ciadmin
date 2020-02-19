import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIRuleGroupUpdateComponent } from 'app/entities/ci-rule-group/ci-rule-group-update.component';
import { CIRuleGroupService } from 'app/entities/ci-rule-group/ci-rule-group.service';
import { CIRuleGroup } from 'app/shared/model/ci-rule-group.model';

describe('Component Tests', () => {
  describe('CIRuleGroup Management Update Component', () => {
    let comp: CIRuleGroupUpdateComponent;
    let fixture: ComponentFixture<CIRuleGroupUpdateComponent>;
    let service: CIRuleGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIRuleGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CIRuleGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CIRuleGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CIRuleGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CIRuleGroup(123);
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
        const entity = new CIRuleGroup();
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
