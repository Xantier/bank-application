import React from 'react';
import { Route } from 'react-router';

import Index from './components/Index.jsx';
import Create from './components/account/Create.jsx';
import Transact from './components/transaction/Transact.jsx';
import TransactionList from './components/transaction/TransactionList.jsx';

var routes = (
    <Route name="index" path="/" handler={ Index }>
      <Route name="create" handler={ Create } />
      <Route name="transact" handler={ Transact } />
      <Route name="list" handler={ TransactionList } />
    </Route>
);

export default routes;