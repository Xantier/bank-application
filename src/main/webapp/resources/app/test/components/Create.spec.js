import React from 'react/addons';
import {expect} from 'chai';
import Create from '../../scripts/components/account/Create.jsx';

describe('Create', () => {

  let {TestUtils} = React.addons;
  let shallowRenderer = TestUtils.createRenderer();
  shallowRenderer.render(<Create />);
  let component = shallowRenderer.getRenderOutput();
  it('should have a div as container', () => {
    expect(component.type).to.equal('div');
  });

  it('should contain an H1 tag with msg', () => {
    expect(component.props.children[0].type).to.be.equal('h1');
    expect(component.props.children[0].props.children).to.be.equal('Create Account');
  });

  it('should contain a form', () => {
    expect(component.props.children[1].type).to.be.equal('form');
  });

  // Don't want to start playing with JS Dom so let's go with shallow renderer. --> traversing a lot

  it('should contain form with 5 inputs', () => {
    // 5x divs for form fields + legend
    expect(component.props.children[1].props.children.props.children.length).to.equal(6);
    console.log(component.state);
  });
});
