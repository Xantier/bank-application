'use strict';

import React from 'react';
import { Link } from 'react-router';

class Header extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
        <header className="clearfix">
          <nav className="navbar navbar-default">
            <div className="navbar-header">
              <a className="navbar-brand" href="#">Teller App</a>
            </div>
            <div>
              <ul className="nav navbar-nav">
                <li>
                  <Link to="create">Create</Link>
                </li>
                <li>
                  <Link to="transact">Transfer</Link>
                </li>
                <li>
                  <Link to="list">List</Link>
                </li>
              </ul>
            </div>
          </nav>
        </header>
    );
  }
}

Header.contextTypes = {
  router: React.PropTypes.func.isRequired
};

export default Header;
