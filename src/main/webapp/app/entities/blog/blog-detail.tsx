import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog.reducer';

export const BlogDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const blogEntity = useAppSelector(state => state.blog.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blogDetailsHeading">
          <Translate contentKey="overcastBlogApp.blog.detail.title">Blog</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blogEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="overcastBlogApp.blog.name">Name</Translate>
            </span>
          </dt>
          <dd>{blogEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="overcastBlogApp.blog.title">Title</Translate>
            </span>
          </dt>
          <dd>{blogEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="overcastBlogApp.blog.description">Description</Translate>
            </span>
          </dt>
          <dd>{blogEntity.description}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="overcastBlogApp.blog.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{blogEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="overcastBlogApp.blog.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{blogEntity.createdDate ? <TextFormat value={blogEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="aboutMe">
              <Translate contentKey="overcastBlogApp.blog.aboutMe">About Me</Translate>
            </span>
          </dt>
          <dd>{blogEntity.aboutMe}</dd>
          <dt>
            <span id="spotifyProfileLink">
              <Translate contentKey="overcastBlogApp.blog.spotifyProfileLink">Spotify Profile Link</Translate>
            </span>
          </dt>
          <dd>{blogEntity.spotifyProfileLink}</dd>
          <dt>
            <Translate contentKey="overcastBlogApp.blog.user">User</Translate>
          </dt>
          <dd>{blogEntity.user ? blogEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/blog" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog/${blogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlogDetail;
