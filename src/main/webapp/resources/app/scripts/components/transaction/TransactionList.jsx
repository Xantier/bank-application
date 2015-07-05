'use strict';

import React from 'react';
import request from 'superagent';
import DropDown from '../common/DropDown.jsx';

class TransactionList extends React.Component {
  constructor(props) {
    super(props);
    this.componentWillMount = this.componentWillMount.bind(this);
    this._retrieve = this._retrieve.bind(this);
    this.state = {};
  }

  // These should be in Stores. See nerd-stack for Flux impl
  componentWillMount() {
    let that = this;
    request.get('/account/loadAll')
        .set('Accept', 'application/json')
        .end(function (err, res) {
          if (res.ok) {
            // hide personal info etc.
            that.setState({accounts: res.body});
            if (res.body.length) {
              that._retrieve(res.body[0].id)
            }
          } else {
            console.log('Unable to Retrieve Accounts');
          }
        });
  }

  _retrieve(child) {
    const url = '/account/load';
    let that = this;
    let accountId = child.refs ? child.refs.selectBox.getDOMNode().value : child;
    request.get(url)
        .query({accountId: accountId})
        .set('Content-Type', 'application/x-www-form-urlencoded')
        .set('Accept', 'application/json')
        .end(function (err, res) {
          if (res.ok) {
            that.setState(getTransactions(res.body));
            console.log(res.body);
          } else {
            console.log('Unable to save transaction');
          }
        });
  }

  getTransactions(data) {

  }

  render() {

    return (
        <div>
          <h1>List Transactions</h1>
          <form className="form-horizontal">
            <DropDown name="Account" ref="listBox" accounts={this.state.accounts} onSelect={this._retrieve}/>
          </form>
        </div>
    );
  }
}

export default TransactionList;
