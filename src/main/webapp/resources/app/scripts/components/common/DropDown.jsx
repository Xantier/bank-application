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
        <form>
          <label htmlFor="this.props.name">{this.props.name.toUpperCase()}</label>
          <select ref="selectBox" name={this.props.name} class="form-control">
            {options}
          </select>
        </form>
    );
  }
}

DropDown.propTypes = {
  accounts: React.PropTypes.array.isRequired,
  name: React.PropTypes.string.isRequired
};

export default DropDown;