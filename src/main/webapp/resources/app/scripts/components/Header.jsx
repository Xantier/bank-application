import React from 'react';
import { Link } from 'react-router';

class Header extends React.Component{

  constructor(props) {
    super(props);
  }

  render() {
    return (
        <header className="clearfix">
          <nav className="clearfix">
            <div className="nav-item">
              <Link to="create">Create</Link>
            </div>
            <div className="nav-item">
              <Link to="transact">Transfer</Link>
            </div>
            <div className="nav-item">
              <Link to="list">List</Link>
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
