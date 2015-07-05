import React from 'react';
import Input from '../common/Input.jsx';
import request from 'superagent';

class Create extends React.Component {

  constructor(props) {
    super(props);
    this._post = this._post.bind(this);
    this._updateState = this._updateState.bind(this);
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
            alert('yay got ' + JSON.stringify(res.body));
          } else {
            alert('Oh no! error ' + res.text);
          }
        });
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
          <formclassName="form-horizontal" onSubmit={this._post}>
            <fieldset>

              <legend>New Account</legend>

              <divclassName="form-group">
                <labelclassName="col-md-4 control-label" htmlFor="name">Account Holder</label>
                <divclassName="col-md-4">
                  <Input name="name" type="text" onChange={this._updateState} required={true}/>
                </div>
              </div>

              <divclassName="form-group">
                <labelclassName="col-md-4 control-label" htmlFor="address">Address</label>
                <divclassName="col-md-4">
                  <Input name="address" type="text" onChange={this._updateState} placeholder="123 Main Street"  required={true}/>
                </div>
              </div>

              <divclassName="form-group">
                <labelclassName="col-md-4 control-label" htmlFor="phoneNumber">Phone Number</label>
                <divclassName="col-md-4">
                  <Input name="phoneNumber" type="text" onChange={this._updateState} placeholder="(000)-555-1234" required={true}/>
                </div>
              </div>

              <divclassName="form-group">
                <labelclassName="col-md-4 control-label" htmlFor="balance">Balance</label>
                <divclassName="col-md-2">
                  <Input name="balance" type="number" onChange={this._updateState} placeholder="0.00" required={true}/>
                </div>
              </div>

              <divclassName="form-group">
                <divclassName="col-md-4">
                  <button id="save" name="save"className="btn btn-primary">Save</button>
                </div>
              </div>

            </fieldset>
          </form>

        </div>
    );
  }
}

export default Create;
