const express = require("express");
const router = express.Router();
var http = require('http');
const Android = require("./android.js")

router.get('/get_data', async (req, res) => {
    try {
      const data = await Android.find();
      res.status(200).json(data)
    }
    catch (error) {
        res.status(400).json(error)
    }
  })

  router.post('/add_data', async (req, res) => {
    try {
        const newObject = await Android(req.body).save();
        console.log("Created: "+newObject)
        res.status(200).json(newObject)
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
  })

module.exports = router;