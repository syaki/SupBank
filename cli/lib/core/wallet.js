const EC = require('elliptic').ec;
const ec = new EC('secp256k1');
const Blockchain = require('./blockchain');
const read_db = require('../utils/read_db');
const write_db = require('../utils/write_db');

const wallets_db_name = 'wallets_db.json';

const Wallet = {
  get_balance: function (address) {
    return Blockchain.get_blockchain()
      .then(data => {
        let balance = 0;

        data.forEach(c => {
          c.transactions.forEach(t => {
            if (t.vin.address === address) {
              balance -= parseInt(t.vin.amount);
            }

            if (t.vout.address === address) {
              balance += parseInt(t.vout.amount);
            }
          })
        });

        return Promise.resolve(balance);
      });
  },
  create_wallet: function () {
    // generator a key pair
    const key = ec.genKeyPair();
    const public_key = key.getPublic('hex');
    const private_key = key.getPrivate('hex');

    return read_db(wallets_db_name, 'utf-8')
      .then(data => {
        data.push({
          publickey: public_key,
          privatekey: private_key,
        });
        data = JSON.stringify(data);
        return write_db(wallets_db_name, data, 'utf-8');
      });
  }
};

module.exports = Wallet;
