// @ flow
const Dao = require('./dao.js');

module.exports = class CategoryDao extends Dao {
    /** Get all categories from database */
    getAll(callback : (number, { length : number}) => mixed){
        super.query('select * from categories', [], callback);
    }
}