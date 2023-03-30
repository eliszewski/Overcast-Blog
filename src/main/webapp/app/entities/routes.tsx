import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Post from './post';
import Blog from './blog';
import Link from './link';
import LinkTheme from './link-theme';
import Rating from './rating';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="post/*" element={<Post />} />
        <Route path="blog/*" element={<Blog />} />
        <Route path="link/*" element={<Link />} />
        <Route path="link-theme/*" element={<LinkTheme />} />
        <Route path="rating/*" element={<Rating />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
