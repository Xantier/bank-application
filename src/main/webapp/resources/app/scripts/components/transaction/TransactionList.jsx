'use strict';

import React from 'react';
import request from 'superagent';
import DropDown from '../common/DropDown.jsx';

class TransactionList extends React.Component {
  constructor(props) {
    super(props);
    this.componentWillMount = this.componentWillMount.bind(this);
    this._retrieve = this._retrieve.bind(this);
    this._updateTransactions = this._updateTransactions.bind(this);
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
              that._retrieve(res.body[0].id);
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
            that._updateTransactions(res.body.transactions);
          } else {
            console.log('Unable to retrieve transactions');
          }
        });
  }

  _updateTransactions(data) {
    this.setState({transactions: data});
  }

  render() {
    let transactions;
    if (this.state.transactions) {
      transactions = this.state.transactions.map(function (trans) {
        return (
            <tr>
              <td>{trans.date.year}-{trans.date.monthValue}-{trans.date.dayOfMonth}</td>
              <td>{trans.amount}</td>
              <td>{trans.transactionType}</td>
              <td>{trans.accountFrom ? trans.accountFrom.name : ''}</td>
            </tr>
        );
      });
    }
    return (
        <div className="row-fluid">
          <div className="span9">

            <h1>List Transactions</h1>
            <form className="form-horizontal">
              <fieldset>
                <DropDown name="Account" ref="listBox" accounts={this.state.accounts} onSelect={this._retrieve}/>
              </fieldset>
            </form>
            <h2>Transactions</h2>
            <table className="table">
              <thead>
                <tr>
                  <th className="col-md-3">Date</th>
                  <th className="col-md-3">Amount</th>
                  <th className="col-md-3">Type</th>
                  <th className="col-md-3">From</th>
                </tr>
              </thead>
              <tbody>
              {transactions}
              </tbody>
            </table>
          </div>
        </div>
    );
  }
}

export default TransactionList;
