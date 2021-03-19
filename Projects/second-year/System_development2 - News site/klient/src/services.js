// @flow
import axios from "axios";
import {sharedComponentData} from "react-simplified";
import {Article, Comment, Likes, oneArticle, PostArticle, PostLikes} from "./models";


/** ArticleService is both a service and a store, it stores all articles for livefeed and registrer components, while the others
* call on a service, which returns a promise with data */
class ArticleService {
    //actually a hybrid of a service and store object
    articles = []; //Stores all the articles needed for livefeed and registrer component.
    newPosts = false; // Used for shared data between components, and with socket connection.
    categories = []; //Stores all the categories from DB.

    /** Get all articles from database, this is actually not a service, but a store method*/
    getAllArticles() : void{
        axios.get<Article[]>('http://localhost:8080/articles').then(res => {
            this.articles = res.data;
            this.newPosts = false;
        });
    }

    /** Get article based on 'article_id' */
    getArticle(article_id : number): Promise<Object>{
        return axios.get<oneArticle[]>(`http://localhost:8080/articles/${article_id}`);
    }

    /** Get all comments on article, based on 'article_id' */
    getComments(article_id : number) : Promise<Object>{
        return axios.get<Comment[]>(`http://localhost:8080/comments/${article_id}`);
    }

    /** Update article based on JSON */
    updateArticle(json : Article) : Promise<Object>{
        return axios.put<Article>('http://localhost:8080/articles', json);

    }

    /** Get all articles based upon priority */
    getArticleByPriority(priority : number) : Promise<Object>{
        return axios.get<Article[]>(`http://localhost:8080/articles/priority/${priority}`);

    }

    /** Get all articles based upon category */
    getArticlesByCategory(category : string) : Promise<Object>{
       return axios.get<Article[]>(`http://localhost:8080/articles/category/${category}`);
    }

    /** Post article to database */
    postArticle(json : Object){
        return axios.post<PostArticle>(`http://localhost:8080/articles`, json).then(res => {
                console.log(res);
                console.log(res.data);
            });
    }

    /** Post comment on article */
    postComment(json : Comment) : Promise<Object>{
        return axios.post<Comment>('http://localhost:8080/comments/add', json);
    }

    /** Get likes on article, based on 'article_id' */
    getLikes(id : number) : Promise<Object>{
        return axios.get<Likes>(`http://localhost:8080/articles/likes/${id}`);
    }

    /** Set likes on article */
    reactToArticle(json : Object) : Promise<Object>{
        return axios.put<PostLikes>(`http://localhost:8080/articles/likes`, json);
    }

    /** Delete article from database (in reality, only setting visible = 0 which is false) */
    deleteArticle(article : Object) : Promise<Object>{
        return axios.delete<Article>(`http://localhost:8080/articles`, { data: article}).then(res => res.data);
    }

    /** Get all categories from database */
    getCategories() : void{
        axios.get("http://localhost:8080/categories").then(res => this.categories = res.data);
    }

    /** Set priority on article */
    setPriority(priority : number, article_id : number) : Promise<Object>{
        let json = {
            "priority" : priority,
            "article_id" : article_id
        };
        return axios.put("http://localhost:8080/articles/priority", json);
    }
}


/** Shared global service that can toogle if the mobile navigation is visible or not */
class MobileMenuDisplayToggle{
    showMobileMenu : boolean = false;

    // Toggles mobile menu
    toggleMobileMenu() : void {
        this.showMobileMenu = !this.showMobileMenu;
    }

    //Turn off mobile menu
    setDisplayFalse() : void{
        this.showMobileMenu = false;
    }
}

//Shared objects that if changed, calls for re-render on components
export let articleService = sharedComponentData(new ArticleService());
export let mobileDisplay = sharedComponentData(new MobileMenuDisplayToggle());