import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './link-theme.reducer';

export const LinkThemeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const linkThemeEntity = useAppSelector(state => state.linkTheme.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="linkThemeDetailsHeading">
          <Translate contentKey="overcastBlogApp.linkTheme.detail.title">LinkTheme</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{linkThemeEntity.id}</dd>
          <dt>
            <span id="isCustom">
              <Translate contentKey="overcastBlogApp.linkTheme.isCustom">Is Custom</Translate>
            </span>
          </dt>
          <dd>{linkThemeEntity.isCustom ? 'true' : 'false'}</dd>
          <dt>
            <span id="customName">
              <Translate contentKey="overcastBlogApp.linkTheme.customName">Custom Name</Translate>
            </span>
          </dt>
          <dd>{linkThemeEntity.customName}</dd>
          <dt>
            <span id="presetTheme">
              <Translate contentKey="overcastBlogApp.linkTheme.presetTheme">Preset Theme</Translate>
            </span>
          </dt>
          <dd>{linkThemeEntity.presetTheme}</dd>
          <dt>
            <Translate contentKey="overcastBlogApp.linkTheme.post">Post</Translate>
          </dt>
          <dd>{linkThemeEntity.post ? linkThemeEntity.post.id : ''}</dd>
          <dt>
            <Translate contentKey="overcastBlogApp.linkTheme.user">User</Translate>
          </dt>
          <dd>{linkThemeEntity.user ? linkThemeEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/link-theme" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/link-theme/${linkThemeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LinkThemeDetail;
