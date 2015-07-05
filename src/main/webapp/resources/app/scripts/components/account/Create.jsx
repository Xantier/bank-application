import React from 'react';
import Input from '../common/Input.jsx';

class Create extends React.Component {

  constructor(props) {
    super(props);
  }

  _post(e) {
    e.preventDefault();
    console.log(this.state);
  }

  _updateState(e) {
    let state = this.state || {};
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  render() {

    return (
        <div>
          <h1>Create Account</h1>
          <form class="form-horizontal" onSubmit={this._post}>
            <fieldset>

              <legend>New Account</legend>

              <div class="form-group">
                <label class="col-md-4 control-label" for="name">Account Holder</label>
                <div class="col-md-4">
                  <Input name="name" type="text" onChange={this._updateState} required={true}/>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-4 control-label" for="address">Address</label>
                <div class="col-md-4">
                  <Input name="address" type="text" onChange={this._updateState} placeholder="123 Main Street"  required={true}/>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-4 control-label" for="phoneNumber">Phone Number</label>
                <div class="col-md-4">
                  <Input name="phoneNumber" type="text" onChange={this._updateState} placeholder="(000)-555-1234" required={true}/>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-4 control-label" for="balance">Balance</label>
                <div class="col-md-2">
                  <Input name="balance" type="number" onChange={this._updateState} placeholder="0.00" required={true}/>
                </div>
              </div>

              <div class="form-group">
                <div class="col-md-4">
                  <button id="save" name="save" class="btn btn-primary">Save</button>
                </div>
              </div>

            </fieldset>
          </form>

        </div>
    );
  }
}

export default Create;
