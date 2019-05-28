/**
 * @description command options list
 */
const cmd_list = {
  c: {
    name: 'create wallet',
    desc: '                              Generates a new key-pair and saves it into the wallet file.',
    controller: 'create_wallet',
    option: '-c',
    args: [],
  },
  g: {
    name: 'get balance',
    desc: '                    Get balance of address.',
    controller: 'get_balance',
    option: '-g',
    args: ['address'],
  },
  l: {
    name: 'list address',
    desc: '                              Lists all addresses from the wallet file.',
    controller: 'list_address',
    option: '-l',
    args: [],
  },
  s: {
    name: 'send coins',
    desc: '  Send AMOUNT of coins from FROM address to TO. Mine block, when <mine> is true.',
    controller: 'send_coins',
    option: '-s',
    args: ['amount', 'from', 'to', 'mine'],
  },
  h: {
    name: 'help',
    desc: '                              Output usage information.',
    controller: '',
    option: '-h',
    args: [],
  },
};

module.exports = cmd_list;
