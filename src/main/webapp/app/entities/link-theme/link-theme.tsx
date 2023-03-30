import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILinkTheme } from 'app/shared/model/link-theme.model';
import { getEntities } from './link-theme.reducer';

export const LinkTheme = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const linkThemeList = useAppSelector(state => state.linkTheme.entities);
  const loading = useAppSelector(state => state.linkTheme.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="link-theme-heading" data-cy="LinkThemeHeading">
        <Translate contentKey="overcastBlogApp.linkTheme.home.title">Link Themes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="overcastBlogApp.linkTheme.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/link-theme/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="overcastBlogApp.linkTheme.home.createLabel">Create new Link Theme</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {linkThemeList && linkThemeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.isCustom">Is Custom</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.customName">Custom Name</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.presetTheme">Preset Theme</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.post">Post</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.linkTheme.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {linkThemeList.map((linkTheme, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/link-theme/${linkTheme.id}`} color="link" size="sm">
                      {linkTheme.id}
                    </Button>
                  </td>
                  <td>{linkTheme.isCustom ? 'true' : 'false'}</td>
                  <td>{linkTheme.customName}</td>
                  <td>
                    <Translate contentKey={`overcastBlogApp.Theme.${linkTheme.presetTheme}`} />
                  </td>
                  <td>{linkTheme.post ? <Link to={`/post/${linkTheme.post.id}`}>{linkTheme.post.id}</Link> : ''}</td>
                  <td>{linkTheme.user ? linkTheme.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/link-theme/${linkTheme.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/link-theme/${linkTheme.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/link-theme/${linkTheme.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="overcastBlogApp.linkTheme.home.notFound">No Link Themes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LinkTheme;
