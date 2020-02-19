import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIRuleGroupDetailComponent } from 'app/entities/ci-rule-group/ci-rule-group-detail.component';
import { CIRuleGroup } from 'app/shared/model/ci-rule-group.model';

describe('Component Tests', () => {
  describe('CIRuleGroup Management Detail Component', () => {
    let comp: CIRuleGroupDetailComponent;
    let fixture: ComponentFixture<CIRuleGroupDetailComponent>;
    const route = ({ data: of({ cIRuleGroup: new CIRuleGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIRuleGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CIRuleGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CIRuleGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cIRuleGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cIRuleGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
