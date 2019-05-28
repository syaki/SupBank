var fs = require('fs');
var path = require('path');

var db_dir = path.join(__dirname, '../db/');

/**
 * @description read database file by promise
 * @param {string} db_name database name
 * @param {string} encoding encoding option
 * @return {Promise} promise
 */
function read_db(db_name, encoding) {
  var promise = new Promise((resolve, reject) => {
    fs.readFile(db_dir + db_name, encoding, (err, data) => {
      if (err) {
        reject('read database file error: ' + err);
      } else {
        data = JSON.parse(data);
        resolve(data);
      }
    });
  });

  return promise;
};

module.exports = read_db;
