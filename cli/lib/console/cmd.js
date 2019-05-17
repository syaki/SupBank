const cmd_list = require('./cmd_list');


var t = require('../controller/send_coins');

/**
 * @description
 * @param {string[]} args command arguments
 * @return {Promise} a promise object
 */
function cmd(args) {
  let c = new Promise((resolve, reject) => {
    switch (args.length) {
      case 3:
        if (args[2] === '-c') {
          resolve(['c', args]);
        } else if (args[2] === '-h') {
          reject('Help docs:');
        } else if (args[2] === '-l') {
          resolve(['l', args]);
        } else {
          reject('Error: arguments error.');
        }
        break;
      case 4:
        if (args[2] === '-g') {
          resolve(['g', args]);
        } else {
          reject('Error: arguments error.');
        }
        break;
      case 7:
        if (args[2] === '-s') {
          resolve(['s', args]);
        } else {
          reject('Error: arguments error.');
        }
        break;
      default:
        reject('Error: arguments error.');
    }
  })
    .then(data => {
      c = cmd_list[data[0]];
      require(`../controller/${c.controller}`)(data[1]);
    })
    .catch(msg => {
      console.log(`${msg}\n`);
      print_help();
    });

  return c;
}

/**
 * @description console log help message
 */
function print_help() {
  console.log('Usage: wallet [options]\n');
  console.log('Options:');
  for (let c in cmd_list) {
    c = cmd_list[c];
    let msg = `  ${c.option}`;

    if (c.args.length > 0) {
      c.args.forEach(a => {
        msg += ` <${a}>`;
      });
    }

    msg += c.desc;
    console.log(msg);
  }
}

module.exports = cmd;
