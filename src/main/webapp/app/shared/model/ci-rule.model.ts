import { Moment } from 'moment';

export interface ICIRule {
  id?: number;
  ruleName?: string;
  critical?: boolean;
  useInReport?: boolean;
  useInMicroservice?: boolean;
  modifyDate?: Moment;
  cIRuleGroupId?: number;
}

export class CIRule implements ICIRule {
  constructor(
    public id?: number,
    public ruleName?: string,
    public critical?: boolean,
    public useInReport?: boolean,
    public useInMicroservice?: boolean,
    public modifyDate?: Moment,
    public cIRuleGroupId?: number
  ) {
    this.critical = this.critical || false;
    this.useInReport = this.useInReport || false;
    this.useInMicroservice = this.useInMicroservice || false;
  }
}
