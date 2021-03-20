
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {Article} from "../articles";
import {articleService} from "../services";
import {Header} from "../header";


describe('Header component', () => {

    it('should render component', () => {
       const component = shallow(<Header/>);
        expect(typeof component).toEqual('object');
    });

    it('should render header logo', () =>{
       const component = shallow(<Header/>);
       const wrapper = component.find('.logoHeader');
       expect(wrapper.length).toBe(1);
    });

    it('should render menubar top', () =>{
       const component = shallow(<Header/>);
       const wrapper = component.find('.menuHeader');
       expect(wrapper.length).toBe(1);
    });

    it('should render livefeed bar', () =>{
       const component = shallow(<Header/>);
       const wrapper = component.find('LiveFeed').dive().find('.rollingNews');
       expect(wrapper.length).toBe(1);
    });



});