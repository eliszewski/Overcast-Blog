import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlog } from 'app/shared/model/blog.model';
import { getEntities } from './blog.reducer';

export const Blog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const blogList = useAppSelector(state => state.blog.entities);
  const loading = useAppSelector(state => state.blog.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="blog-heading" data-cy="BlogHeading">
        <Translate contentKey="overcastBlogApp.blog.home.title">Blogs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="overcastBlogApp.blog.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/blog/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="overcastBlogApp.blog.home.createLabel">Create new Blog</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {blogList && blogList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.createdBy">Created By</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.aboutMe">About Me</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.spotifyProfileLink">Spotify Profile Link</Translate>
                </th>
                <th>
                  <Translate contentKey="overcastBlogApp.blog.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogList.map((blog, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/blog/${blog.id}`} color="link" size="sm">
                      {blog.id}
                    </Button>
                  </td>
                  <td>{blog.name}</td>
                  <td>{blog.title}</td>
                  <td>{blog.description}</td>
                  <td>{blog.createdBy}</td>
                  <td>{blog.createdDate ? <TextFormat type="date" value={blog.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{blog.aboutMe}</td>
                  <td>{blog.spotifyProfileLink}</td>
                  <td>{blog.user ? blog.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/blog/${blog.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog/${blog.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog/${blog.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="overcastBlogApp.blog.home.notFound">No Blogs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Blog;
