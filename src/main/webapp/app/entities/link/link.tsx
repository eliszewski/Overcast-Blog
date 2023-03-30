import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILink } from 'app/shared/model/link.model';
import { getEntities } from './link.reducer';

export const Link = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const linkList = useAppSelector(state => state.link.entities);
  const loading = useAppSelector(state => state.link.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="link-heading" data-cy="LinkHeading">
        <Translate contentKey="overcastBlogApp.link.home.title">Links</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="overcastBlogApp.link.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/link/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="overcastBlogApp.link.home.createLabel">Create new Link</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {linkList && linkList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="overcastBlogApp.link.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.link.url">Url</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.link.linkTheme">Link Theme</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.link.post">Post</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {linkList.map((link, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/link/${link.id}`} color="link" size="sm">
                      {link.id}
                    </Button>
                  </td>
                  <td>{link.url}</td>
                  <td>{link.linkTheme ? <Link to={`/link-theme/${link.linkTheme.id}`}>{link.linkTheme.id}</Link> : ''}</td>
                  <td>{link.post ? <Link to={`/post/${link.post.id}`}>{link.post.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/link/${link.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/link/${link.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/link/${link.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="overcastBlogApp.link.home.notFound">No Links found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Link;
