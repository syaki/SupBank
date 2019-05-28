const Wallets = require('../core/wallets');

/**
 * @description list all of the wallets' address in wallets_db
 */
function list_address() {
  Wallets.get_all_address()
    .then(data => {
      data.forEach((w, i) => {
        console.log(`wallet ${i + 1} address:`);
        console.log(`${w.publickey}\n`);
      });
    })
    .catch(msg => {
      console.log(msg);
    });
}

module.exports = list_address;
