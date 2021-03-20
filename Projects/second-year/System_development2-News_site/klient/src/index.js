// @flow
/* eslint eqeqeq: "off" */

import * as React from 'react';
import { Component } from 'react-simplified';
import { HashRouter, Route} from 'react-router-dom';
import ReactDOM from 'react-dom';
import { Header } from "./header";
import {GridArticle, Article} from "./articles";
import {Submit} from "./submit";
import {Footer} from "./footer";
import {Registrering, RegistreringEdit} from "./reg";
import {articleService} from "./services";
import {MobileMenu} from "./mobileMenu";

/** Content is parent component for GridArticle, sets up the content bar and fills it
 * with GridArticles in a grid, the articles that are displayed are based on which menu link is clicked
 * i.e. if category = "Politikk" then only articles with that category is displayed.
 * If user is on front page/'Topp' then only articles with priority =1 is displayed. */
export class Content extends Component <{category : string}>{
    articles : [] = [];

    constructor(props : {category : string}) {
        super(props);

        // Renders grid articles based on either category or priority
        if(this.props.category === "Top"){
            articleService.getArticleByPriority(1).then(res => (this.articles = res.data));
        } else{
            articleService.getArticlesByCategory(this.props.category).then(res => (this.articles = res.data));
        }
    }

    render(){
        return(
            <div className="margin-top-grid">
                <div className="contentContainer">
                    <div className="contentGrid">
                        {this.articles.map(e =>
                            <GridArticle key={"a"+e.article_id} headline={e.headline} picture={e.picture} category={e.category} content={e.content} id={e.article_id}></GridArticle>
                        )}
                    </div>
                </div>
            </div>
        )
    }
}

/** Renders the all the components on the site,
 * Header and footer is displayed statically, but content is based on menu option */
const root = document.getElementById('root');
if (root)
  ReactDOM.render(
    <HashRouter>
      <div>
          <Header/>
        <Route exact path="/" component={() => <Content category="Top"/>} />
        <Route path="/registrer" component={Registrering}/>
        <Route path="/submit" component={Submit} />
        <Route path="/category/livsstil" component={() => <Content category="Livsstil"/>} />
        <Route path="/category/politikk" component={() => <Content category="Politikk"/>} />
        <Route path="/mobilemenu" component={MobileMenu} />
        <Route path="/category/sport" component={() => <Content category="Sport"/>} />
        <Route exact path="/article/:id" component={Article}/>
        <Route exact path="/article/:id/edit" component={RegistreringEdit}/>
        <Footer/>
      </div>
    </HashRouter>,
    root
  );
