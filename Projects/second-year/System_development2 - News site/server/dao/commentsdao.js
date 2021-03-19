// @ flow
const Dao = require('./dao.js');

module.exports = class CommentsDao extends Dao {

    /** Get all comments based upon 'article_id' */
    getComments(callback : (number, { length : number}) => mixed, article_id : number){
        super.query('select * from comments where article_id=?', [article_id], callback);
    }

    /** Post a new comment to an Article */
    postComment(callback : (number, { length : number}) => mixed, list : []){
        super.query('insert into comments(c_id, nickname, text_comment, article_id) values(default, ?, ?, ?)', list, callback);
    }


}