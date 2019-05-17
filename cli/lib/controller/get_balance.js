const Wallet = require('../core/wallet');

const getbalance = function (args) {
  address = args[3];
  Wallet.get_balance(address)
    .then(data => {
      console.log(data);
    })
    .catch(msg => {
      console.log(msg);
    });
}

module.exports = getbalance;
