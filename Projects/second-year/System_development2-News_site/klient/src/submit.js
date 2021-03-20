// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import {Button, Row, Column, Card} from "./widgets";
import {articleService} from "./services";

import { createHashHistory } from 'history';


const history = createHashHistory(); // Use history.push(...) to programmatically change path, for instance after successfully saving a student



/** Submit component, is where a user can type in and publish a new post to the website */
export class Submit extends Component{
    render(){
        return(
            <div className="margin-top-reg commonFont paddingSubmitEdit">
            <Card title="Legg til ny artikkel">
                <form>
                    <Row>
                        <Column width={4}>Overskrift</Column>
                        <Column width={8}>
                    <input type="text" placeholder="Overskrift" id="submitHeadline"/>
                        </Column>
                    </Row>
                    <Row>
                        <Column width={4}>Innhold:</Column>
                    <Column width={8}>
                        <textarea placeholder="Skriv inn teksten din her" id="submitContentText"/>
                    </Column>
                    </Row>
                    <Row>
                        <Column width={4}>Bilde url</Column>
                    <Column width={8}>
                        <input type="text" placeholder="Skriv inn bilde link her" id="submitPictureUrl"/>
                    </Column>
                    </Row>
                    <Row>
                        <Column width={4}>Forfatter</Column>
                        <Column width={8}>
                            <input type="text" placeholder="Forfatter" id="submitAuthorText"/>
                        </Column>
                    </Row>
                    <Row>
                        <Column width={4}>Legg til i kategori:</Column>
                        <Column width={8}>
                    <select id="submitCategorySelector" className="custom-select">
                        {articleService.categories.map(e =>
                        <option>{e.category}</option>
                        )}
                    </select>
                        </Column>
                    </Row>
                    <Row>
                        <Column width={4}>Prioritet:</Column>
                        <Column width={8}>
                            <select id="submitPriority" className="custom-select">
                                <option>1</option>
                                <option>2</option>
                            </select>
                        </Column>
                    </Row>
                    <br/>
                    <Button.Success onClick={this.submitArticle}>Send inn</Button.Success>
                </form>
            </Card>
            </div>

        )
    }

    /** Used for flow type checking return value of HTML input elements */
    getElementValue(id: string) {
        let e  = document.querySelector('#'+id);
        if(e instanceof HTMLTextAreaElement || e instanceof HTMLInputElement || e instanceof HTMLSelectElement || e instanceof HTMLOptionElement){
            return e.value;
        } else{
            return null;
        }

    }


    /** submitArticle posts new article to database and refreshes shared array
     * with call to articleservice.getAllArticles()
     */
    submitArticle() : void{
        let headline = this.getElementValue('submitHeadline');
        let content = this.getElementValue('submitContentText');
        let pictureUrl = this.getElementValue('submitPictureUrl');
        let author = this.getElementValue('submitAuthorText');
        let category = this.getElementValue('submitCategorySelector');
        let priority = this.getElementValue('submitPriority');

        //Also update database
        let json = {
            headline : headline,
            content : content,
            category : category,
            picture : pictureUrl,
            priority : priority,
            author : author
        };


        articleService.postArticle(json).then(articleService.getAllArticles).catch((error: Error) => alert("Noe gikk galt"));
        alert("Artikkelen har blitt lagt til")
        history.push('/registrer'); //Redirects back when finished.
    }

}