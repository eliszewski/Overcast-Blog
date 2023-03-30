import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ILink } from 'app/shared/model/link.model';

export interface IRating {
  id?: number;
  stars?: number;
  date?: string | null;
  user?: IUser | null;
  link?: ILink | null;
}

export const defaultValue: Readonly<IRating> = {};
