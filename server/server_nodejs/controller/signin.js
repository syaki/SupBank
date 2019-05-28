var fnv = require('fnv-plus');
var jwt = require('jsonwebtoken');
var read_db = require('../utils/read_db');

// account database file name
var account_db_name = 'account_db.json';
// readfile options: encoding
var encoding = 'utf-8';

var response = {
  ack: '',
  data: {
    msg: '',
    token: '',
  },
};

/**
 * @description sign in controller
 * @param {Request} req request object
 * @param {Response}res response object
 */
function sign_in(req, res) {
  var req_emailaddress = req.body.emailaddress;
  var password_hash = fnv.hash(req.body.password, 64).str();

  read_db(account_db_name, encoding)
    .then(data => {
      var check_result = check_email_and_password(req_emailaddress, password_hash, data);

      if (check_result) {
        var token = jwt.sign({
          emailaddress: req_emailaddress,
          password_hash: password_hash
        }, 'shhhhh');

        response = {
          ack: 'success',
          data: {
            msg: 'sign in success',
            token: token
          }
        };
      } else {
        response.ack = 'sign in fail';
        response.data.msg = 'email address or password error';
      }

      res.send(JSON.stringify(response));
    })
    .catch(msg => {
      response.ack = 'fail';
      response.data.msg = msg;
      res.send(JSON.stringify(response));
    });
};

/**
 * @description check email and password hash right
 * @param {string} e request send emailaddress
 * @param {string} p request send password
 * @param {object} d database file data
 * @return {boolean} check result
 */
function check_email_and_password(e, p, d) {
  for (a of d) {
    if (a.emailaddress === e) {
      if (a.password_hash === p) {
        return true;
      }
    }
  }

  return false;
}

module.exports = sign_in;
