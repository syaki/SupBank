const read_db = require('../utils/read_db');

// blockchain database file name
var blockchain_db_name = 'blockchain_db.json';
// readfile options: encoding
var encoding = 'utf-8';

var response = {
  ack: '',
  data: {
    msg: '',
    txlist: [],
  },
};

function get_new_txs(req, res) {
  read_db(blockchain_db_name, encoding)
    .then(data => {
      if (data.length < 6) {
        response.ack = 'success';
        response.data.txlist = data;
      } else {
        response.ack = 'success';
        response.data.txlist = data.slice(-6);
      }

      res.end(JSON.stringify(response));
    })
    .catch(msg => {
      response.ack = 'fail';
      response.data.msg = msg;
      res.end(JSON.stringify(response));
    })
}

module.exports = get_new_txs;
