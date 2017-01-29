'use strict';

import React from 'react';
import DropDown from '../common/DropDown.jsx';
import request from 'superagent';
import Input from '../common/Input.jsx';

class Transact extends React.Component {
  constructor(props) {
    super(props);
    this.componentWillMount = this.componentWillMount.bind(this);
    this._updateState = this._updateState.bind(this);
    this._transferOrLodge = this._transferOrLodge.bind(this);
    this._post = this._post.bind(this);
    this.state = {
      accounts: [],
      lodge: true,
      header: 'Lodge',
      buttonText: 'Transfer'
    };
  }

  _updateState(e) {
    this.setState({[e.target.name]: e.target.value});
  }

  _transferOrLodge(e) {
    e.preventDefault();
    let lodge = !this.state.lodge;
    this.setState({lodge: lodge});
    this.setState({header: lodge ? 'Lodge' : 'Transfer'});
    this.setState({buttonText: lodge ? 'Transfer' : 'Lodge'});
  }

  _post() {
    if (!this.state.lodge && this.refs.fromBox.refs.selectBox.getDOMNode().value === this.refs.toBox.refs.selectBox.getDOMNode().value) {
      console.log('please don\'t');
      return;
    }
    const url = this.state.lodge ? '/account/lodge' : '/account/transfer';
    let data = {amount: this.state.amount};
    if (this.state.lodge) {
      data.accountId = this.refs.toBox.refs.selectBox.getDOMNode().value;
    } else {
      data.accountFromId = this.refs.fromBox.refs.selectBox.getDOMNode().value;
      data.accountToId = this.refs.toBox.refs.selectBox.getDOMNode().value;
    }
    request.post(url)
        .send(data)
        .set('Accept', 'application/json')
        .end(function (err, res) {
          if (res.ok) {
            console.log(res.body);
          } else {
            console.log('Unable to save transaction');
          }
        });
  }

  componentWillMount() {
    let that = this;
    request.get('/account/loadAll')
        .set('Accept', 'application/json')
        .end(function (err, res) {
          if (res.ok) {
            // hide personal info etc.
            that.setState({accounts: res.body});
          } else {
            console.log('Unable to Retrieve Accounts');
          }
        });
  }

  render() {

    return (
        <div className="row-fluid">
          <div className="span9">
            <h1>{this.state.header}</h1>

            <form className="form-horizontal">
              <fieldset>
                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="transferOrLodge">Transfer/ Lodge? </label>
                  <div className="col-md-4">
                    <button id="transferOrLodge" name="transferOrLodge" className="btn btn-primary" onClick={this._transferOrLodge}>Switch to {this.state.buttonText}</button>
                  </div>
                </div>

            { !this.state.lodge ? <DropDown name="From" ref="fromBox" accounts={this.state.accounts} /> : null }
                <DropDown name="To" ref="toBox" accounts={this.state.accounts} />
                <div className="form-group">
                  <label className="col-md-4 control-label" for="amount">Amount</label>
                  <div className="col-md-4">
                    <Input name="amount" type="number" onChange={this._updateState} placeholder="0.00" required={true}/>
                  </div>
                </div>

                <div className="form-group">
                  <div className="col-md-4">
                    <button id="save" name="save"className="btn btn-primary" onClick={this._post} >{this.state.header}</button>
                  </div>
                </div>

              </fieldset>
            </form>
          </div>
        </div>
    );
  }
}

export default Transact;
