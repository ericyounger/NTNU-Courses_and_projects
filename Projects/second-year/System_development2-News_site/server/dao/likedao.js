// @ flow
const Dao = require('./dao.js');

module.exports = class ArticleDao extends Dao {

    /** Get the number of likes based on 'article_id' */
    getLikes(callback : (number, { length : number}) => mixed, article_id : number){
        super.query('select likes from article where visible=1 and article_id=?', [article_id], callback);
    }

    /** Set likes on an article */
    setLikes(callback : (number, { length : number}) => mixed, list : []){
        super.query('update article set likes=? where article_id=?', list, callback);
    }

}