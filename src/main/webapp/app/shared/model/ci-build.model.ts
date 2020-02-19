import { Moment } from 'moment';

export interface ICIBuild {
  id?: number;
  moduleName?: string;
  moduleVersion?: string;
  branch?: string;
  ciResult?: string;
  sonarResult?: string;
  buildDate?: Moment;
  sonarProject?: string;
}

export class CIBuild implements ICIBuild {
  constructor(
    public id?: number,
    public moduleName?: string,
    public moduleVersion?: string,
    public branch?: string,
    public ciResult?: string,
    public sonarResult?: string,
    public buildDate?: Moment,
    public sonarProject?: string
  ) {}
}
