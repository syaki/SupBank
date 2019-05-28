var fnv = require('fnv-plus');
var write_db = require('../utils/write_db');
var read_db = require('../utils/read_db');

// account database file name
var account_db_name = 'account_db.json';
// readfile options: encoding
var encoding = 'utf-8';

var response = {
  ack: '',
  data: {
    msg: '',
  },
};

/**
 * @description sign up controller
 * @param {Request} req request object
 * @param {Response}res response object
 */
function signup(req, res) {
  var req_emailaddress = req.body.emailaddress;
  var req_password = req.body.password;

  read_db(account_db_name, encoding)
    .then(data => {
      var check_result = check_do_email_signup(req_emailaddress, data);

      if (check_result) {
        response.ack = 'fail';
        response.data.msg = 'this email has been signed up';
        res.send(JSON.stringify(response));
      } else {
        storeNewAccount(req_emailaddress, req_password, data, res);
      }
    })
    .catch(msg => {
      response.ack = 'fail';
      response.data.msg = msg;
      res.send(JSON.stringify(response));
    });
}

/**
 * @description store new account in account_db database
 * @param {string} e email address
 * @param {string} p password
 * @param {object} d account_db data
 * @param {Response} res response object
 */
function storeNewAccount(e, p, d, res) {
  var password_hash = fnv.hash(p, 64).str();
  var id = d.length + 1;
  var new_account = {
    id: id,
    emailaddress: e,
    password_hash: password_hash
  };

  d[d.length] = new_account;

  d = JSON.stringify(d);
  write_db(account_db_name, d, encoding)
    .then(() => {
      response.ack = 'success';
      response.data.msg = 'sign up success';
      res.send(JSON.stringify(response));
    })
    .catch(msg => {
      response.ack = 'fail';
      response.data.msg = msg;
      res.send(JSON.stringify(response));
    });
}

/**
 * @description check does email have been signed up
 * @param {string} e request send email address
 * @param {object} d database file data
 * @return {boolean} check result
 */
function check_do_email_signup(e, d) {
  for (a of d) {
    if (a.emailaddress === e) {
      return true;
    }
  }

  return false;
}

module.exports = signup;
