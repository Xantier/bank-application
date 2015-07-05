import React from 'react/addons';
import {expect} from 'chai';
import DropDown from '../../../scripts/components/common/DropDown.jsx';

describe('DropDown', () => {

  let {TestUtils} = React.addons;
  let shallowRenderer = TestUtils.createRenderer();
  shallowRenderer.render(<DropDown accounts={[]}/>);
  let component = shallowRenderer.getRenderOutput();

  it('should have a div as container', () => {
    expect(component.type).to.equal('select');
  });

  it('should have class form-control', () => {
    expect(component._store.props.class).to.equal('form-control');
  });
});