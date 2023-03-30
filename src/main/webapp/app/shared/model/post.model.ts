import dayjs from 'dayjs';
import { ILink } from 'app/shared/model/link.model';
import { IBlog } from 'app/shared/model/blog.model';
import { ILinkTheme } from 'app/shared/model/link-theme.model';

export interface IPost {
  id?: number;
  header?: string | null;
  content?: string | null;
  date?: string;
  links?: ILink[] | null;
  blog?: IBlog | null;
  linkThemes?: ILinkTheme[] | null;
}

export const defaultValue: Readonly<IPost> = {};
