const express = require("express");
const { SecureNative, EventTypes } = require('@securenative/sdk');
const app = express();
const PORT = 3001;
const bodyParser = require('body-parser');

var cookieParser = require('cookie-parser')

/*const securenative = new SecureNative({
  apiKey: 'eyJhcHBJZCI6IlNOLTA3NTVDNkY3NzQiLCJyZXNvdXJjZXMiOlsiU25GbG93TWFuYWdlciIsIlNuRmxvd0V4ZWN1dG9yIiwiU25JcENsYXNzaWZpY2F0aW9uIiwiU25HZW9WZWxvY2l0eSIsIlNuQWNjb3VudENvbXByb21pc2VkIiwiU25SdWxlRW5naW5lIiwiU25Db2xsZWN0b3IiXX0=',
  apiUrl: 'http://localhost:5005/api/v1'
});*/

//STG
/*const securenative = new SecureNative('98FD735A08DC3FB6CDFE47638AFF889215473754', {
  apiUrl: 'https://api.securenative-stg.com/collector/api/v1'
});*/

const securenative = new SecureNative('98FD735A08DC3FB6CDFE47638AFF889215473754');

app.use(express.static(__dirname + '/public'));
//app.use(cookieParser());
app.use(bodyParser.json());


app.get('/', (req, res) => {
  res.sendfile(__dirname + '/index.html');
});

app.get("/login", (req, res) => {
  securenative.track({
    eventType: EventTypes.LOG_IN,
    user: {
      id:  'misha@securenative.com',
      email: 'misha@securenative.com'
    }
  }, req);

  console.log('Sent login event');
  res.redirect("/");
});

app.get("/logout", (req, res) => {
  securenative.track({
    eventType: EventTypes.LOG_OUT,
    ip: "127.0.0.1",
    remoteIp: "127.0.0.1",
    user: {
      id: '789',
      name: 'Alex',
      email: 'alex@securenative.com'
    }
  }, req);

  console.log('Sent logout event');
  res.redirect("/");
});

app.get("/failed", (req, res) => {
  securenative.track({
    eventType: EventTypes.LOG_IN_FAILURE,
    ip: "127.0.0.1",
    remoteIp: "127.0.0.1",
    user: {
      id: '789',
      name: 'Alex',
      email: 'alex@securenative.com'
    }
  }, req);

  console.log('Sent failed login event');
  res.redirect("/");
});


app.get("/verify", async (req, res) => {
  const data = await securenative.verify({
    eventType: EventTypes.verify,
    ip: "193.71.189.188",
    remoteIp: "127.0.0.1",
    user: {
      id: '789',
      name: 'Alex',
      email: 'alex@securenative.com'
    }
  }, req);

  console.log(data);
  res.redirect("/");
});


app.post("/webhook", securenative.middleware.verifyWebhook, (req, res) => {
  console.log('webhook executed', req.body);
  res.send('got webhook');
});

app.listen(PORT, function () {
  console.log(`Server is running ${PORT}`);
});