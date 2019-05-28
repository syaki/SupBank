var express = require('express');
var router = express.Router();

var router_list = [
  {
    options: 'post',
    url: '/signin',
    controller: '../controller/signin',
  },
  {
    options: 'post',
    url: '/signup',
    controller: '../controller/signup',
  },
  {
    options: 'post',
    url: '/s',
    controller: '../controller/search',
  },
  {
    options: 'get',
    url: '/get_new_txs',
    controller: '../controller/get_new_txs',
  },
];

var routes = router_list.map(v => {
  router[v.options](v.url, require(v.controller));
  return router;
});

module.exports = routes;
