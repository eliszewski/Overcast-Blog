import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { getEntity, updateEntity, createEntity, reset } from './blog.reducer';

export const BlogUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const blogEntity = useAppSelector(state => state.blog.entity);
  const loading = useAppSelector(state => state.blog.loading);
  const updating = useAppSelector(state => state.blog.updating);
  const updateSuccess = useAppSelector(state => state.blog.updateSuccess);

  const handleClose = () => {
    navigate('/blog');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);

    const entity = {
      ...blogEntity,
      ...values,
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
      ? {
          createdDate: displayDefaultDateTime(),
        }
      : {
          ...blogEntity,
          createdDate: convertDateTimeFromServer(blogEntity.createdDate),
          user: blogEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="overcastBlogApp.blog.home.createOrEditLabel" data-cy="BlogCreateUpdateHeading">
            <Translate contentKey="overcastBlogApp.blog.home.createOrEditLabel">Create or edit a Blog</Translate>
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
                  id="blog-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('overcastBlogApp.blog.name')} id="blog-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('overcastBlogApp.blog.title')}
                id="blog-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('overcastBlogApp.blog.description')}
                id="blog-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('overcastBlogApp.blog.createdBy')}
                id="blog-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('overcastBlogApp.blog.createdDate')}
                id="blog-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('overcastBlogApp.blog.aboutMe')}
                id="blog-aboutMe"
                name="aboutMe"
                data-cy="aboutMe"
                type="textarea"
              />
              <ValidatedField
                label={translate('overcastBlogApp.blog.spotifyProfileLink')}
                id="blog-spotifyProfileLink"
                name="spotifyProfileLink"
                data-cy="spotifyProfileLink"
                type="text"
              />
              <ValidatedField id="blog-user" name="user" data-cy="user" label={translate('overcastBlogApp.blog.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blog" replace color="info">
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

export default BlogUpdate;
