const Wallet = require('../core/wallet');

/**
 * @description create a pair key as a wallet and store in wallets_db
 */
function create_wallet() {
  Wallet.create_wallet()
    .then((data) => {
      data = JSON.parse(data);
      data = data[data.length - 1];
      console.log('Create wallet success!\n');
      console.log(`this is your address (public key):\n${data.publickey}\n`);
      console.log(`this is your private key (keep this secret! To sign transactions):\n${data.privatekey}`);
    })
    .catch(msg => {
      console.log(msg);
    });
}

module.exports = create_wallet;
