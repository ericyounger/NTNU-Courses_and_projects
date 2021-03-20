// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import {Button} from "./widgets";
import {Card} from "./widgets";
import {articleService} from "./services";


/** CommentField is a Container for having published posts(Comments component), and to publish new ones(AddComment component).
 * It is used beneath every full-page article. */
export class CommentField extends Component<{article_id : number}>{
    Button : React.Node= <Button.Success onClick={this.addComment}>Publiser kommentar</Button.Success>;
    comments : [] = [];
    render(){
        return(
            <div className="commentBox">
                <Card title="Kommentarer">
                {this.comments.map(e =>
                    <Comments nickName = {e.nickname} textComment={e.text_comment}/>
                )}
            </Card>
                <AddComment Button={this.Button}/>
            </div>
        )
    }

    mounted() : void{
        // Get comments based on article id
        articleService.getComments(this.props.article_id).then(res => this.comments = res.data);
    }

    //For flow type checking
    getElementByIdValue(id: string) {
        let e  = document.querySelector('#'+id);
        if(e instanceof HTMLInputElement){
            return e.value;
        } else{
            return null;
        }

    }

    //For flow type checking
    getElementByClassNameValue(id: string) {
        let e  = document.querySelector('.'+id);
        if(e instanceof HTMLTextAreaElement || e instanceof HTMLInputElement){
            return e.value;
        } else{
            return null;
        }

    }

    /** Submits a new article to database and add it to its own local array to re-render component */
    addComment() : void{
        let article_id : number = this.props.article_id;
        let commentTxt = this.getElementByClassNameValue('commentInput');
        let nickName = this.getElementByClassNameValue('commentUsername');

        // If no user input on Nickname field, then it is set to 'Anonym'
        if(!nickName){
            nickName = "Anonym";
        }

        // Creates a json to send to Rest API.
        let commentData : Object = {
            "nickname" : nickName,
            "text_comment" : commentTxt,
            "article_id" : article_id
        };

        //Uses service to publish comment on article
        articleService.postComment(commentData).then(res => {
            alert("Kommentaren din ble lagt til");
            this.comments.push(commentData);
        });

    }
}

/** Comments component displays all the published comments using Cards.
 * It is used by root component 'CommentField' */
export class Comments extends Component <{textComment : string, nickName : string}> {
    render(){
        return(
            <div className="list-group">
                <Card title={this.props.nickName}>{this.props.textComment}</Card>
            </div>
        )
    }
}

/** AddComment component is a field where a user can write in their comment, nickname and publish their own comment
 * It is used by root component 'CommentField' */
export class AddComment extends Component<{Button : React.Node}>{
    render(){
        return(
            <div>
                <form>
                    <textarea placeholder="Hva synes du om saken?" className="commentInput">{""}</textarea>
                    <input  className="commentUsername" type="text" placeholder="Brukernavn" maxLength={24}/>
                    Anonymt hvis blankt
                    <div className="float-right">
                        {this.props.Button}
                    </div>
                </form>
            </div>
        )
    }
}