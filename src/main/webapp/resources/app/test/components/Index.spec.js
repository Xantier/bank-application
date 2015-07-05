import React from 'react/addons';
import {expect} from 'chai';
import Index from '../../scripts/components/Index.jsx';

describe('Index', () => {
  let {TestUtils} = React.addons;
  let shallowRenderer = TestUtils.createRenderer();
  shallowRenderer.render(<Index />);
  let component = shallowRenderer.getRenderOutput();

  it('should have a div as container', () => {
    expect(component.type).to.equal('div');
  });

  it('should contains an H1 tag with msg', () => {
    expect(component.props.children.type).to.be.equal('h1');
    expect(component.props.children.props.children).to.be.equal('Hello World');
  });
});
