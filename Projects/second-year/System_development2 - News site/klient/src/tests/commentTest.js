
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {AddComment, CommentField, Comments} from "../comments";
import {articleService} from "../services";


describe('comment', () => {
    const a = jest.spyOn(articleService, "getComments").mockResolvedValue([]);


    it('should render CommentField component', () => {
        const component = shallow(<CommentField/>);
        expect(typeof component).toEqual('object');
    });

    it('should render Comments component', () => {
        const component = shallow(<Comments textComment={"lol"} nickName={"tester"}/>)
        expect(typeof component).toEqual('object');
    });

    it('should have commentBox', () =>{
        const component = shallow(<CommentField article_id={1}/>);
        const wrapper = component.find('.commentBox');
        expect(wrapper.length).toBe(1);
    });

    it('should render addComment component', () =>{
        const component = shallow(<AddComment Button={""}/>);
        expect(typeof component).toEqual('object');
    });






});