// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import { NavLink } from 'react-router-dom';
import {CommentField} from "./comments";
import {Row, Column} from "./widgets";
import {articleService} from "./services";
import {LikeBox} from "./likes";

/** GridArticle, is used with the grid system in Content component, GridArticle is one grid item
 * which displays the thumbnail/teaser post of the articles. Basically a container for grid items. */
export class GridArticle extends Component <{picture : string, category : string, headline: string, id :number}>{
    categoryTag : string;

    constructor(props: {picture : string, category : string, headline: string, id :number}){
        super(props);
        if(this.props.category === "Politikk"){
            this.categoryTag = "category-tag-politics-grid" ;
        } else if(this.props.category === "Livsstil"){
            this.categoryTag = "category-tag-lifestyle-grid";
        } else if(this.props.category === "Sport"){
            this.categoryTag = "category-tag-sports-grid";
        }
    }

    render(){
        return(
            <div>
                <NavLink to={"/article/" + this.props.id} >
                    <div className="article">
                        <div className={this.categoryTag}> {this.props.category}</div>
                        <div className="articleImg"> <img src={this.props.picture} alt="" /></div>
                        <div className="headline">{this.props.headline}</div>
                    </div>
                </NavLink>
            </div>
        )
    }
}

/** Article is a component that show one article in it's entirety.
 * Sets up a container for the article and also a CommentField beneath every article */
export class Article extends Component<{ match: { params: { id: number } } }>{
    headline : string = "";
    content : string = "";
    post_date : number = 0;
    picture : string = "";
    author : string = "";
    category  : string = "";
    categoryTag  : string= "";


    render(){
        return(
            <div className="margin-top-grid">
            <div className="articleContainer commonFont">
                <img src={this.picture} alt=""/>
                <h1>{this.headline}</h1>
                <div className={this.categoryTag}>{this.category}</div>

                <Row>
                    <Column width={8}>
                <div className="author"><b>Skrevet av:</b> {this.author}</div>
                    </Column>
                    <Column width={4}><LikeBox article_id={this.props.match.params.id}/>
                    </Column>
                </Row>
                <div className="postDateArticle">{this.post_date.toString().replace("T", " ").substr(0,16)}</div>
                <div className="breadtext">{this.content}</div>

            </div>
                <CommentField article_id = {this.props.match.params.id}></CommentField>
            </div>
        )
    }

    mounted() : void {

        articleService.getArticle(this.props.match.params.id).then(res => {
            const article : Article = res.data[0];
            if(!article){
                alert("noe galt skjedde");
            }

            this.headline = article.headline;
            this.content = article.content;
            this.post_date = article.post_date;
            this.picture = article.picture;
            this.author = article.author;
            this.category = article.category;

            if(this.category === "Politikk"){
                this.categoryTag = "category-tag-politics" ;
            } else if(this.category === "Livsstil"){
                this.categoryTag = "category-tag-lifestyle";
            } else if(this.category === "Sport"){
                this.categoryTag = "category-tag-sports";
            }
        });

    }
}