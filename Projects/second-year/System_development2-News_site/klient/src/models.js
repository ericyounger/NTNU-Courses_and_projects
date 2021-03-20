// @flow

/**
 *
 * Models of classes, used for flow typechecking
 *
 */

/** Model for receiving the number of likes from database */
export class Likes{
    likes : number;
}

/** Model for json to post likes on article on database */
export class PostLikes{
    likes : number;
    article_id : number;
}

/** Model for comments, both post and get */
export class Comment{
    nickname : string;
    text_comment : string;
    article_id : number;
}

/** Model for the return value from Rest API */
export class Article {
    article_id: number;
    headline: string;
    content: string;
    priority: number;
    picture: string;
    post_date: string;
    category: string;
    author: string;
}

/** Model for how a JSON to post article should look like */
export class PostArticle{
    headline : string;
    content : string;
    category : string;
    picture : string;
    priority: number;
    author: string;
}

/** Model for receiving JSON from Rest API */
export class oneArticle{
        article_id : number;
        headline : string;
        content : string;
        priority : number;
        picture : string;
        post_date : string;
        category : string;
        author : string;
}

