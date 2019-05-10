const read_db = require('../utils/read_db');

const wallets_db_name = 'wallets_db.json';

const Wallets = {
  get_all_address: function () {
    return read_db(wallets_db_name, 'utf-8');
  },

  check_address: function(address) {
    return this.get_all_address()
      .then(data => {
        let flag = data.some(d => {
          if (address === d.publickey) {
            return true;
          }
        });

        if (flag) {
          return Promise.resolve();
        } else {
          return Promise.reject(`Error: ${address} not found.`);
        }
      });
  },
};

module.exports = Wallets;
