// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import {Button, Row, Column} from "./widgets";

import {articleService} from "./services";


/** LikeBox component is a component which is displayed on full-page articles
 * The LikeBox displays the amount of likes the articles has, and also allows for
 * increasing or decreasing that number of likes */
export class LikeBox extends Component <{article_id : number}>{
    likes : number = 0;

    render(){
        return(
            <div className="likeBox">
                <Row>
                    <div className="noOfLikes">
                    <Column width={2}>{this.likes}</Column>
                    </div>
                    <div className="likeButtons">
                   <Button.Success onClick={this.increaseLikes}> + </Button.Success>
                    <Button.Danger onClick={this.decreaseLikes}>-</Button.Danger>
                    </div>
                </Row>
            </div>
        )
    }

    mounted() : void {
        articleService.getLikes(this.props.article_id).then(res => (this.likes = res.data[0].likes));
    }

    //Update article's likes and changes own local variable.
    increaseLikes() : void{
        this.likes +=1;
        let json = {
            "likes" : this.likes,
            "article_id" : this.props.article_id,
        };

        articleService.reactToArticle(json).then();

        //If the number of likes on articles is more than 10 than priority is automatically changed to 1. Front page material.
        if(this.likes > 10){
            articleService.setPriority(1,this.props.article_id);
        }
    }

    //Update article's likes and changes own local variable.
    decreaseLikes() : void{
        this.likes -=1;
        let json = {
            "likes" : this.likes,
            "article_id" : this.props.article_id,
        };
        //Set the number of likes on article.
        articleService.reactToArticle(json).then();

        //If the number of likes on articles is less than 10 than priority is automatically changed to 2. Not front page material.
        if(this.likes <10){
            articleService.setPriority(2, this.props.article_id);
        }
    }
}