import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiadminTestModule } from '../../../test.module';
import { CIRuleDetailComponent } from 'app/entities/ci-rule/ci-rule-detail.component';
import { CIRule } from 'app/shared/model/ci-rule.model';

describe('Component Tests', () => {
  describe('CIRule Management Detail Component', () => {
    let comp: CIRuleDetailComponent;
    let fixture: ComponentFixture<CIRuleDetailComponent>;
    const route = ({ data: of({ cIRule: new CIRule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiadminTestModule],
        declarations: [CIRuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CIRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CIRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cIRule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cIRule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
