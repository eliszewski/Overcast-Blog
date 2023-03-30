import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './link.reducer';

export const LinkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const linkEntity = useAppSelector(state => state.link.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="linkDetailsHeading">
          <Translate contentKey="overcastBlogApp.link.detail.title">Link</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{linkEntity.id}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="overcastBlogApp.link.url">Url</Translate>
            </span>
          </dt>
          <dd>{linkEntity.url}</dd>
          <dt>
            <Translate contentKey="overcastBlogApp.link.linkTheme">Link Theme</Translate>
          </dt>
          <dd>{linkEntity.linkTheme ? linkEntity.linkTheme.id : ''}</dd>
          <dt>
            <Translate contentKey="overcastBlogApp.link.post">Post</Translate>
          </dt>
          <dd>{linkEntity.post ? linkEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/link" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/link/${linkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LinkDetail;
