'use strict';

import React from 'react';

class Input extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <input id={this.props.name} name={this.props.name}
            type={this.props.type} placeholder={this.props.placeholder || this.props.name}
            required={this.props.required} onChange={this.props.onChange} className="form-control input-md"/>
    );
  }
}

Input.propTypes = {
  name: React.PropTypes.string.isRequired,
  type: React.PropTypes.string.isRequired,
  placeholder: React.PropTypes.string,
  required: React.PropTypes.bool,
  onChange: React.PropTypes.func
};

export default Input