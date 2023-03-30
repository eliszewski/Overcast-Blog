import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/post">
        <Translate contentKey="global.menu.entities.post" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blog">
        <Translate contentKey="global.menu.entities.blog" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/link">
        <Translate contentKey="global.menu.entities.link" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/link-theme">
        <Translate contentKey="global.menu.entities.linkTheme" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rating">
        <Translate contentKey="global.menu.entities.rating" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
