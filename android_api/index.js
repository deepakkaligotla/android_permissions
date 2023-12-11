const mongoose = require('mongoose');
const express = require('express')
const os = require('os')
const http = require("http");
const url = require('url')

const router = require('./route.js');
require('dotenv').config();
const config = require('config')
const android = express()


mongoose.connect(process.env.ATLAS_URI);
const database = mongoose.connection;

const networkInterfaces = os.networkInterfaces();
const publicIP = networkInterfaces.en0 && networkInterfaces.en0[1].address;
const io = require('socket.io')(http);

android.use('/', router)
android.use(express.json());

const server = http.createServer(android);

io.on('connection', (socket) => {
    console.log('a node connected')
    socket.on("page-response", (response) => {
        clientResponseRef.send(response)
    })
})

const port = process.env.YOUR_PORT || process.env.PORT || 3000

server.listen(port,  () => {
    database.on('error', (error) => {
        console.log(`Android MongoDB connection failed on Node server at: http://${publicIP}:${port}`)
    })
    
    database.once('connected', () => {
        console.log(`Android MongoDB connected on Node server at: http://${publicIP}:${port}`);
    })
});