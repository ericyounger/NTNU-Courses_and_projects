
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {Article} from "../articles";
import {articleService} from "../services";
import {Header} from "../header";
import {Registrering} from "../reg";


describe('Registrering component', () => {
    const test = jest.fn();
    const a = jest.spyOn(articleService, "getAllArticles").mockResolvedValue([]);


    it('should render component', () => {
        const component = shallow(<Registrering/>);
        expect(typeof component).toEqual('object');
    });






});