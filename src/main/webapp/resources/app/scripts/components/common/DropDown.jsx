'use strict';

import React from 'react';

class DropDown extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const selects = this.props.accounts.map(function (account) {
      return <option value={account.id}>{account.name}</option>;
    });
    return (
    <select class="form-control">
      {selects}
    </select>
    );
  }
}

DropDown.propTypes = {
  accounts: React.PropTypes.array.isRequired
};

export default DropDown;