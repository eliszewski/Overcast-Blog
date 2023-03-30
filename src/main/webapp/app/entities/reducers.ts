import post from 'app/entities/post/post.reducer';
import blog from 'app/entities/blog/blog.reducer';
import link from 'app/entities/link/link.reducer';
import linkTheme from 'app/entities/link-theme/link-theme.reducer';
import rating from 'app/entities/rating/rating.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  post,
  blog,
  link,
  linkTheme,
  rating,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
