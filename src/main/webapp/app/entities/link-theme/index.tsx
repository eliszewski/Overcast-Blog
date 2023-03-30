import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LinkTheme from './link-theme';
import LinkThemeDetail from './link-theme-detail';
import LinkThemeUpdate from './link-theme-update';
import LinkThemeDeleteDialog from './link-theme-delete-dialog';

const LinkThemeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LinkTheme />} />
    <Route path="new" element={<LinkThemeUpdate />} />
    <Route path=":id">
      <Route index element={<LinkThemeDetail />} />
      <Route path="edit" element={<LinkThemeUpdate />} />
      <Route path="delete" element={<LinkThemeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LinkThemeRoutes;
