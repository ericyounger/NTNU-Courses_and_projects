
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {Article} from "../articles";
import {articleService} from "../services";
import {Registrering} from "../reg";
import {LikeBox} from "../likes";


describe('likeBox tests', () => {

    //const a = jest.spyOn(articleService, "getAllArticles").mockResolvedValue(article);
    const wrapper = shallow(<LikeBox article_id={5}/>);


    it('initially', () => {
        let instance = LikeBox.instance();
        expect(typeof instance).toEqual('object');
    });

    it('get likes from likeBox', () =>{
        let instance = LikeBox.instance();
        expect(instance.likes).toBe(0);
    });

    it('set likes in likeBox', () =>{
       let instance = LikeBox.instance();
       instance.increaseLikes();
       expect(instance.likes).toBe(1);
    });

});