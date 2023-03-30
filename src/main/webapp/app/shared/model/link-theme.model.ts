import { IPost } from 'app/shared/model/post.model';
import { IUser } from 'app/shared/model/user.model';
import { ILink } from 'app/shared/model/link.model';
import { Theme } from 'app/shared/model/enumerations/theme.model';

export interface ILinkTheme {
  id?: number;
  isCustom?: boolean;
  customName?: string | null;
  presetTheme?: Theme | null;
  post?: IPost | null;
  user?: IUser | null;
  link?: ILink | null;
}

export const defaultValue: Readonly<ILinkTheme> = {
  isCustom: false,
};
