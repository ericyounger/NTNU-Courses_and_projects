
import * as React from 'react';
import { Component } from 'react-simplified';
import {shallow} from "enzyme";
import {Article} from "../articles";
import {articleService} from "../services";


describe('Article tests', () => {
    let article = {
        "article_id" : 5,
        "headline" : "Test",
        "content" : "Masse tekstinnhold",
        "priority" : 1,
        "picture" : "url",
        "post_date" : "2019-09-05T20:08:37.000Z",
        "category" : "Politikk",
        "author" : "Tester"
    };

    const a = jest.spyOn(articleService, "getArticle").mockResolvedValue(article);
    const wrapper = shallow(
        <Article
            required={true}
            match={{params: {id: 5}, isExact: true, path: "", url: ""}}
        />

    );

    it('should render component', () =>{
        expect(typeof wrapper).toEqual('object');
    });

    it('should set up article variables', () => {
        let instance = Article.instance();
        expect(instance.headline).toMatch("");
        expect(instance.content).toMatch("");
        expect(instance.post_date).toBe(0);
        expect(instance.author).toMatch("");
        expect(instance.category).toMatch("");
    });


});