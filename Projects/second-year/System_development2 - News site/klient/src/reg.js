// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import {Button, Card, Row, Column} from "./widgets";
import {NavLink} from "react-router-dom";
import {articleService} from "./services";
import { createHashHistory } from 'history';


let history = createHashHistory(); // Use history.push(...) to programmatically change path, for instance after successfully saving a student

/** Registrering component is a page where the user has listed all articles and can click buttons to edit, delete and submit new articles,
 * This component, lists up the articles, while 'RegistreringsEdit' edits the articles, and 'Submit' posts new articles. */
export class Registrering extends Component{

    render(){
        return(
            <div className="margin-top-reg commonFont paddingSubmitEdit">
            <Card title="Saker som ligger inne:">
                {articleService.articles.map(e =>
                    <Card title=""><Row>
                        <Column>
                        {e.headline}
                        </Column>
                            <Button.Danger onClick={a => this.delete(e.article_id)}>Slett</Button.Danger>
                            <NavLink to={"article/" + e.article_id + "/edit"}>
                            <Button.Light onClick={()=>{}}>Rediger</Button.Light>
                            </NavLink>
                    </Row></Card>
                )}
                <NavLink to="/submit">
                    <br/>
                        <Button.Success onClick={()=>{}}>Legg til ny artikkel</Button.Success>
                </NavLink>
            </Card>
            </div>
        )
    }


    mounted() : void {
        //Get all articles on mount.
        articleService.getAllArticles();
    }


    /** delete method for deleting an article. */
    delete(id : number) : void{
        let article : Object = articleService.articles.find(e => e.article_id === id);

        // Delete article from DB.
        articleService.deleteArticle(article).then(res => {
            
            alert("Artikkel ble slettet");
            const index = articleService.articles.indexOf(article);
            articleService.articles.splice(index, 1); // remove article from local array.
        }).catch((error: Error) => alert(error.message));
    }
}

/** RegistreringsEdit is a component where the user can edit an article */
export class RegistreringEdit extends Component <{ match: { params: { id: number } } }>{
    headline : string = "";
    category : string = "";
    content : string = "";
    priority : string = "";
    picture : string = "";

    render(){
        return(
            <div className="margin-top-reg commonFont paddingSubmitEdit">
           <Card title={"Redigerer " + this.headline}>
                   <form>
                       <Row>
                           <Column width={4}>Overskrift</Column>
                           <Column width={8}>
                               <input
                                   type="text"
                                   value={this.headline}
                                   onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.headline = event.target.value)}
                               />
                           </Column>
                       </Row>
                       <Row>
                           <Column width={4}>Innholdstekst</Column>
                           <Column width={8}>
                               <textarea
                                   type="text"
                                   value={this.content}
                                   onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.content = event.target.value)}
                               />
                           </Column>
                       </Row>
                       <Row>
                           <Column width={4}>Bilde url</Column>
                           <Column width={8}>
                               <input
                                   type="text"
                                   value={this.picture}
                                   onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.picture = event.target.value)}
                               />
                           </Column>
                       </Row>
                       <Row>
                           <Column width={4}>Kategori</Column>
                           <Column width={8}>
                               <select id="editCategorySelector" value={this.category} onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.category = event.target.value)} className="custom-select">
                                   {articleService.categories.map(e =>
                                   <option>{e.category}</option>
                                   )}
                               </select>
                           </Column>
                       </Row>
                       <Row>
                           <Column width={4}>Prioritet</Column>
                           <Column width={8}>
                               <select id="editPriority" value={this.priority} onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.priority = event.target.value)} className="custom-select">
                                   <option>1</option>
                                   <option>2</option>
                               </select>
                           </Column>
                       </Row>
                       <Row>
                           <Column width={6}>
                               <br/>
                               <Button.Success onClick={this.save}>Lagre</Button.Success>
                           </Column>
                       </Row>

                   </form>
           </Card>
            </div>
        )}

    mounted(){
        //Sets all the local variable on mount, so that user can edit.
        articleService.getArticle(this.props.match.params.id).then(res => {
            const article : Object = res.data[0];
            this.headline = article.headline;
            this.category = article.category;
            this.content = article.content;
            this.picture = article.picture;
            this.priority = article.priority;
        });
    }

    // Used for flow type checking
    getElementByClassNameValue(id: string) {
        let e  = document.querySelector('.'+id);
        if(e instanceof HTMLTextAreaElement || e instanceof HTMLInputElement || e instanceof HTMLSelectElement || e instanceof HTMLOptionElement){
            return e.value;
        } else{
            return null;
        }
    }

    /** Saves the article and updates local array and adds to Database. */
    save(){
        let categorySelected = this.getElementByClassNameValue('custom-select');
        let json : Object = {
            "headline" : this.headline,
            "category" : categorySelected,
            "picture" : this.picture,
            "priority" : parseInt(this.priority),
            "content" : this.content,
            "article_id" : parseInt(this.props.match.params.id)
        };

        let article = articleService.articles.find(e => e.article_id === json.article_id);
        let index = articleService.articles.indexOf(article);

        articleService.articles.splice(index,1, article);
        articleService.updateArticle(json).then( res => {
            articleService.getAllArticles();

        });
                history.push('/registrer');
    }

}