import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILinkTheme } from 'app/shared/model/link-theme.model';
import { getEntities as getLinkThemes } from 'app/entities/link-theme/link-theme.reducer';
import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { ILink } from 'app/shared/model/link.model';
import { getEntity, updateEntity, createEntity, reset } from './link.reducer';

export const LinkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const linkThemes = useAppSelector(state => state.linkTheme.entities);
  const posts = useAppSelector(state => state.post.entities);
  const linkEntity = useAppSelector(state => state.link.entity);
  const loading = useAppSelector(state => state.link.loading);
  const updating = useAppSelector(state => state.link.updating);
  const updateSuccess = useAppSelector(state => state.link.updateSuccess);

  const handleClose = () => {
    navigate('/link');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLinkThemes({}));
    dispatch(getPosts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...linkEntity,
      ...values,
      linkTheme: linkThemes.find(it => it.id.toString() === values.linkTheme.toString()),
      post: posts.find(it => it.id.toString() === values.post.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...linkEntity,
          linkTheme: linkEntity?.linkTheme?.id,
          post: linkEntity?.post?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="overcastBlogApp.link.home.createOrEditLabel" data-cy="LinkCreateUpdateHeading">
            <Translate contentKey="overcastBlogApp.link.home.createOrEditLabel">Create or edit a Link</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="link-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('overcastBlogApp.link.url')}
                id="link-url"
                name="url"
                data-cy="url"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="link-linkTheme"
                name="linkTheme"
                data-cy="linkTheme"
                label={translate('overcastBlogApp.link.linkTheme')}
                type="select"
              >
                <option value="" key="0" />
                {linkThemes
                  ? linkThemes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="link-post" name="post" data-cy="post" label={translate('overcastBlogApp.link.post')} type="select">
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/link" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LinkUpdate;
