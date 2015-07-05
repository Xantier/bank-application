'use strict';

import React from 'react';

class DropDown extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let options;
    if (this.props.accounts && this.props.accounts.length) {
      options = this.props.accounts.map(function (account) {
        return <option value={account.id}>{account.name}</option>;
      });
    }
    return (
        <div className="col-md-4">
          <label htmlFor="this.props.name">{this.props.name.toUpperCase()}</label>
          <select ref="selectBox" name={this.props.name} className="form-control" onChange={this.props.onSelect ? this.props.onSelect.bind(null, this) : null}>
            {options}
          </select>
        </div>
    );
  }
}

DropDown.propTypes = {
  accounts: React.PropTypes.array.isRequired,
  name: React.PropTypes.string.isRequired,
  onSelect: React.PropTypes.func
};

export default DropDown;
