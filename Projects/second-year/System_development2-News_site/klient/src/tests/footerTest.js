
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {Footer} from "../footer";


describe('Footer component', () => {

    it('should render component', () => {
        const component = shallow(<Footer/>);
        expect(typeof component).toEqual('object');
    });

    it('should render footerBar', () =>{
        const component = shallow(<Footer/>);
        const wrapper = component.find('.footerBar');
        expect(wrapper.length).toBe(1);
    });

    it('should render textboxes', () =>{
       const component = shallow(<Footer/>);
       const wrapper = component.find('FooterBox');
       expect(wrapper.length).toBe(3);

    });




});