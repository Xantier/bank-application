import React from 'react/addons';
import {expect} from 'chai';
import Input from '../../../scripts/components/common/Input.jsx';

describe('Input', () => {

  let {TestUtils} = React.addons;
  let shallowRenderer = TestUtils.createRenderer();
  shallowRenderer.render(<Input />);
  let component = shallowRenderer.getRenderOutput();

  it('should have a div as container', () => {
    expect(component.type).to.equal('input');
  });

  it('should accept 5 props', () =>{
    expect(component._store.props).to.include.keys('name', 'type', 'placeholder', 'required', 'onChange');
  });

  // it should warn if required proptype is not present. --> JS Dom needed for testing.

});