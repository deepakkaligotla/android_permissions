const express = require("express");
const api = require("./api.js");
var bodyParser = require('body-parser')

const api_route_app = express();

api_route_app.use(bodyParser.json({ extended: false }));
api_route_app.use(bodyParser.urlencoded({ extended: false }))
api_route_app.use(bodyParser.json())
api_route_app.use(express.json({ limit: '20mb' }))
api_route_app.use(express.static('images'))

api_route_app.use((request, response, next)=>{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "*");
    next();
});

api_route_app.use(express.json({ limit: "50mb" }));
api_route_app.use(api)

api_route_app.get('/*', (req, res) => {
    var pathname = url.parse(req.url).pathname
    var obj = {
        pathname: pathname,
        method: "get",
        parms: req.query
    }
    io.emit("page-request", obj)
    clientResponseRef = res
})

api_route_app.get('/*', (req, res) => {
    var pathname = url.parse(req.url).pathname
    var obj = {
        pathname: pathname,
        method: "post",
        parms: req.body
    }
    io.emit("page-request", obj)
    clientResponseRef = res
})

module.exports = api_route_app