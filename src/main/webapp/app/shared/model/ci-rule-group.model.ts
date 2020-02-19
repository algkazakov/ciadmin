import { ICIRule } from 'app/shared/model/ci-rule.model';

export interface ICIRuleGroup {
  id?: number;
  ruleGroupName?: string;
  critical?: boolean;
  type?: string;
  contains?: ICIRule[];
}

export class CIRuleGroup implements ICIRuleGroup {
  constructor(
    public id?: number,
    public ruleGroupName?: string,
    public critical?: boolean,
    public type?: string,
    public contains?: ICIRule[]
  ) {
    this.critical = this.critical || false;
  }
}
