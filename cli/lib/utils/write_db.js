var fs = require('fs');
var path = require('path');

var db_dir = path.join(__dirname, '../../db/');

/**
 * @description write data in database file by promise
 * @param {string} db_name database name
 * @param {string} data the data to write
 * @param {string} encoding encoding option
 * @return {Promise} promise
 */
function write_db(db_name, data, encoding) {
  var promise = new Promise((resolve, reject) => {
    fs.writeFile(db_dir + db_name, data, encoding, err => {
      if (err) {
        reject('write database file error: ' + err);
      } else {
        resolve(data);
      }
    });
  });

  return promise;
}

module.exports = write_db;
