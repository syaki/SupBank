const SHA256 = require('crypto-js/sha256');
const read_db = require('../utils/read_db');
const write_db = require('../utils/write_db');
const Blockchain = require('./blockchain');

const wallets_db_name = 'wallets_db.json';

const Transaction = {
  new_transaction: function (amount, from, to) {
    let transactions = [{
      vin: {
        amount: parseInt(amount),
        address: from,
        sign: ''
      },
      vout: {
        amount: parseInt(amount),
        address: to
      }
    }];

    return this.sign(from, transactions)
      .then(() => {
        return Blockchain.new_block(transactions)
      });
  },

  sign: function (from, transactions) {
    return read_db(wallets_db_name)
      .then(data => {
        let private_key = '';
        data.forEach(w => {
          if (w.publickey === from) {
            private_key = w.private_key;
          }
        });

        const tx_hash = SHA256(JSON.stringify(transactions)).toString();
        // const sig = private_key.sign(tx_hash, 'base64');
        const sig = SHA256(private_key + tx_hash).toString();

        transactions[0].vin.sign = sig;

        return Promise.resolve();
      });
  }
};

module.exports = Transaction;
