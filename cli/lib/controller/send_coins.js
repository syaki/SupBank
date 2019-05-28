const Wallets = require('../core/wallets');
const Wallet = require('../core/wallet');
const Transaction = require('../core/transaction');
const Blockchain = require('../core/blockchain');

const send_coins = function (args) {
  const amount = args[3];
  const from = args[4];
  const to = args[5];
  const mine = args[6];

  Wallets.check_address(from)
    .then(() => {
      if (mine === 'true') {
        return Blockchain.mine_block(from);
      } else {
        return Wallet.get_balance(from);
      }
    })
    .then(balance => {
      if (balance < amount) {
        return Promise.reject('\nError: not sufficient balance.');
      } else {
        return Transaction.new_transaction(amount, from, to);
      }
    })
    .then(() => {
      console.log('\ntransaction success');
    })
    .catch(msg => {
      console.log(msg);
    })
};

module.exports = send_coins;
