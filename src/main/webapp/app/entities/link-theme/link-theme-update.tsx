import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ILink } from 'app/shared/model/link.model';
import { getEntities as getLinks } from 'app/entities/link/link.reducer';
import { ILinkTheme } from 'app/shared/model/link-theme.model';
import { Theme } from 'app/shared/model/enumerations/theme.model';
import { getEntity, updateEntity, createEntity, reset } from './link-theme.reducer';

export const LinkThemeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const posts = useAppSelector(state => state.post.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const links = useAppSelector(state => state.link.entities);
  const linkThemeEntity = useAppSelector(state => state.linkTheme.entity);
  const loading = useAppSelector(state => state.linkTheme.loading);
  const updating = useAppSelector(state => state.linkTheme.updating);
  const updateSuccess = useAppSelector(state => state.linkTheme.updateSuccess);
  const themeValues = Object.keys(Theme);

  const handleClose = () => {
    navigate('/link-theme');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPosts({}));
    dispatch(getUsers({}));
    dispatch(getLinks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...linkThemeEntity,
      ...values,
      post: posts.find(it => it.id.toString() === values.post.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          presetTheme: 'MUSICOFTHEDAY',
          ...linkThemeEntity,
          post: linkThemeEntity?.post?.id,
          user: linkThemeEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="overcastBlogApp.linkTheme.home.createOrEditLabel" data-cy="LinkThemeCreateUpdateHeading">
            <Translate contentKey="overcastBlogApp.linkTheme.home.createOrEditLabel">Create or edit a LinkTheme</Translate>
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
                  id="link-theme-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('overcastBlogApp.linkTheme.isCustom')}
                id="link-theme-isCustom"
                name="isCustom"
                data-cy="isCustom"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('overcastBlogApp.linkTheme.customName')}
                id="link-theme-customName"
                name="customName"
                data-cy="customName"
                type="text"
              />
              <ValidatedField
                label={translate('overcastBlogApp.linkTheme.presetTheme')}
                id="link-theme-presetTheme"
                name="presetTheme"
                data-cy="presetTheme"
                type="select"
              >
                {themeValues.map(theme => (
                  <option value={theme} key={theme}>
                    {translate('overcastBlogApp.Theme.' + theme)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="link-theme-post"
                name="post"
                data-cy="post"
                label={translate('overcastBlogApp.linkTheme.post')}
                type="select"
              >
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="link-theme-user"
                name="user"
                data-cy="user"
                label={translate('overcastBlogApp.linkTheme.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/link-theme" replace color="info">
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

export default LinkThemeUpdate;
