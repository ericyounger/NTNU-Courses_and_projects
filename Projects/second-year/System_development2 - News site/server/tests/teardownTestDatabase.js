// @flow
/** Tears down database after finished tests and deployment */
module.exports = async function() {
    await global.__MONGOD__.end();
};
