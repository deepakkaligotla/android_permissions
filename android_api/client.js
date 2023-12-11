const sockerServerUrl = "https://kaligotla-in-61c2d1318f30.herokuapp.com/"
const hostToLive = "http://localhost"

const socket = require('socket.io-client')(sockerServerUrl)
const superagent = require('superagent')

socket.on('connect', function(){
    console.log("connected")
})

socket.on('disconnect', function(){
    console.log("connection lost")
})

socket.on('page-request', function(data){
    var path = data.pathname
    var method = data.method
    var parms = data.params

    var localhostUrl = hostToLive + path

    if(method=="get")executeGet(localhostUrl, params)
    else if(method=="post")executePost(localhostUrl, params)
})

function executeGet(url, params) {
    superagent.get(url)
    .query(params)
    .end((err, response) => {
        if(err) {return console.log(err)}
        socket.emit('page-response', response.text)
    })
}

function executePost(url, params) {
    superagent.post(url)
    .query(params)
    .end((err, response) => {
        if(err) {return console.log(err)}
        socket.emit('page-response', response.text)
    })
}