'use strict';

import React from 'react';
import Input from '../common/Input.jsx';
import request from 'superagent';

class Create extends React.Component {

  constructor(props) {
    super(props);
    this._post = this._post.bind(this);
    this._updateState = this._updateState.bind(this);
    this.state = {};
  }

  _post(e) {
    e.preventDefault();
    const data = this.state;
    request
        .post('/account/create')
        .send(data)
        .set('Accept', 'application/json')
        .end(function (err, res) {
          if (res.ok) {
            console.log(res.body);
          } else {
            alert('Oh no! error ' + res.text);
          }
        });
  }

  _updateState(e) {
    let state = this.state;
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  render() {

    return (
        <div className="row-fluid">
          <div className="span9">
            <h1>Create Account</h1>
            <form className="form-horizontal" onSubmit={this._post}>
              <fieldset>

                <legend>New Account</legend>

                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="name">Account Holder</label>
                  <div className="col-md-4">
                    <Input name="name" type="text" onChange={this._updateState} required={true}/>
                  </div>
                </div>

                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="address">Address</label>
                  <div className="col-md-4">
                    <Input name="address" type="text" onChange={this._updateState} placeholder="123 Main Street" required={true}/>
                  </div>
                </div>

                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="phoneNumber">Phone Number</label>
                  <div className="col-md-4">
                    <Input name="phoneNumber" type="text" onChange={this._updateState} placeholder="(000)-555-1234" required={true}/>
                  </div>
                </div>

                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="balance">Balance</label>
                  <div className="col-md-2">
                    <Input name="balance" type="number" onChange={this._updateState} placeholder="0.00" required={true}/>
                  </div>
                </div>

                <div className="form-group">
                  <div className="col-md-4">
                    <button id="save" name="save"className="btn btn-primary">Save</button>
                  </div>
                </div>

              </fieldset>
            </form>
          </div>
        </div>
    );
  }
}

export default Create;
