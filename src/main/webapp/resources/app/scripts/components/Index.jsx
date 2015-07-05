import React from 'react';
import { RouteHandler } from 'react-router';
import Header from './Header.jsx'

class Index extends React.Component {

  render() {
    return (
        <div>
          <Header />
          <div className="content">
            <RouteHandler/>
          </div>
        </div>
    );
  }

}

export default Index;