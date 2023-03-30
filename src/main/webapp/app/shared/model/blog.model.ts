import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IPost } from 'app/shared/model/post.model';

export interface IBlog {
  id?: number;
  name?: string | null;
  title?: string;
  description?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  aboutMe?: string | null;
  spotifyProfileLink?: string | null;
  user?: IUser | null;
  posts?: IPost[] | null;
}

export const defaultValue: Readonly<IBlog> = {};
