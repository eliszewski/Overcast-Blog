import { ILinkTheme } from 'app/shared/model/link-theme.model';
import { IPost } from 'app/shared/model/post.model';
import { IRating } from 'app/shared/model/rating.model';

export interface ILink {
  id?: number;
  url?: string;
  linkTheme?: ILinkTheme | null;
  post?: IPost | null;
  ratings?: IRating[] | null;
}

export const defaultValue: Readonly<ILink> = {};
