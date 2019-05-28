var jwt = require('jsonwebtoken');
var read_db = require('../utils/read_db');

// account database file name
var account_db_name = 'account_db.json';
// blockchain database file name
var blockchain_db_name = 'blockchain_db.json';
// readfile options: encoding
var encoding = 'utf-8';

var response = {
  ack: '',
  data: {
    msg: '',
    searchlist: [],
  },
};

function search(req, res) {
  var kw = req.body.kw;
  var token = req.body.token;

  if (!token) {
    response.ack = 'fail';
    response.data.msg = 'Please sign in your wallet.';
    res.send(JSON.stringify(response));
  } else {

    var emailaddress = jwt.verify(token, 'shhhhh').emailaddress;
    var password_hash = jwt.verify(token, 'shhhhh').password_hash;

    read_db(account_db_name, encoding)
      .then(data => {
        let flag = data.some(d => {
          if (d.emailaddress === emailaddress && d.password_hash === password_hash) {
            return true;
          }
        });

        if (flag) {
          return Promise.resolve();
        } else {
          return Promise.reject('wallet error. Please re sign in your wallet!');
        }
      })
      .then(() => {
        return read_db(blockchain_db_name, encoding);
      })
      .then(data => {
        let flag = data.some(d => {
          if (d.hash === kw) {
            response.data.searchlist[0] = d;
            return true;
          }
        });

        if (flag) {
          return Promise.resolve();
        } else {
          return Promise.reject('can not find this block.');
        }
      })
      .then(() => {
        response.ack = 'success';
        res.send(JSON.stringify(response));
      })
      .catch(msg => {
        response.ack = 'fail';
        response.data.msg = msg;
        res.send(JSON.stringify(response));
      })
  }
}

module.exports = search;
