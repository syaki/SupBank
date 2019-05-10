const cmd = require('./console/cmd');

/**
 * @description wallet cli tool entry
 */
function entry() {
  const args = process.argv;

  process.title = 'wallet';
  process.version = 'v1.0.0';

  cmd(args);
}

module.exports = entry;
