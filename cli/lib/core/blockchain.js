const SHA256 = require('crypto-js/sha256');
const read_db = require('../utils/read_db');
const write_db = require('../utils/write_db');

const blockchain_db_name = 'blockchain_db.json';

const difficulty = 3;

const block = {
  timestamp: Date.now(),
  prevblockhash: '',
  hash: '',
  nonce: 0,
  height: 0,
  transactions: [{
    vin: {
      amount: 0,
      address: '',
      sign: ''
    },
    vout: {
      amount: 0,
      address: ''
    }
  }],
};

const Blockchain = {
  get_blockchain: function () {
    return read_db(blockchain_db_name, 'utf-8');
  },

  mine_block: function (address) {
    let transactions = [{
      vin: {
        amount: 0,
        address: '',
        sign: ''
      },
      vout: {
        amount: 10,
        address: address
      }
    }]
    return this.new_block(transactions)
      .then(() => {
        return Promise.reject('\nmine success');
      });
  },

  new_block: function (transactions) {
    const self = this;
    return read_db(blockchain_db_name, 'utf-8')
      .then(data => {
        const last_block = data[data.length - 1];
        block.prevblockhash = SHA256(JSON.stringify(last_block)).toString();
        block.height = last_block.height + 1;
        block.transactions = transactions;
        block.hash = SHA256(JSON.stringify(block)).toString();

        self.pow();

        data.push(block);

        return write_db(blockchain_db_name, JSON.stringify(data), 'utf-8');
      });
  },

  pow: function () {
    while (block.hash.substring(0, difficulty) !== Array(difficulty + 1).join('0')) {
      block.nonce++;
      block.hash = SHA256(JSON.stringify(block)).toString();
    }
  }
};

module.exports = Blockchain;
