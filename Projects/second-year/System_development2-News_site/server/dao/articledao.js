// @ flow
const Dao = require('./dao.js');

module.exports = class ArticleDao extends Dao {
    /** Get all articles */
    getAll(callback : (number, { length : number}) => mixed) {
        super.query('select article_id, headline, content, priority, picture, post_date, category, author from article where visible=1 limit 20', [], callback);
    }

    /** Get one article based upon 'article_id' */
    getOne(callback : (number, { length : number}) => mixed, article_id : number){
        super.query('select article_id, headline, content, priority, picture, post_date, category, author from article where article_id=? and visible=1 limit 20', [article_id], callback);
    }

    /** Posts a new article to database, based on JSON with different values */
    createOne(callback : (number, { length : number}) => mixed, list: []){
        super.query('insert into article(article_id, headline, post_date, category, picture, priority, visible, content, author) values (default,?,default,?,?,?,1,?,?)', list, callback);
    }

    /** Deletes one article from database by setting variable visible to false(0) */
    deleteOne(callback: (number, { length : number}) => mixed, article_id : number){
        super.query('UPDATE article SET visible = 0 WHERE article_id=?',[article_id], callback);
    }

    /** Updates an article based on JSON */
    updateOne(callback: (number, { length : number}) => mixed, list : []){
        super.query('update article set headline=?, category=?, picture=?, priority=?, content=? where article_id=?', list, callback);
    }

    /** Set priority on article based on 'article_id' and 'priority' from JSON */
    setPriority(callback : (number, { length : number}) => mixed, list : []){
        super.query('update article set priority=? where article_id=?', list, callback);
    }

    /** Get all articles based on category */
    getByCategory(callback : (number, { length : number}) => mixed, category : string){
        super.query('select article_id, headline, content, priority, picture, post_date, category, author from article where category=? and visible=1 limit 20', [category], callback);
    }

    /** Get all articles based upon priority */
    getByPriority(callback : (number, { length : number}) => mixed, priority : number){
        super.query('select article_id, headline, likes, content, priority, picture, post_date, category, author from article where priority=? and visible=1 order by likes desc limit 20', [priority], callback);
    }

}