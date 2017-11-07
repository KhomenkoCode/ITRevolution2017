var ws;
connect();

function $_GET(key) {
    var s = window.location.search;
    s = s.match(new RegExp(key + '=([^&=]+)'));
    return s ? s[1] : false;
}

function connect() {
    var issue = $_GET("num");
    ws = new WebSocket("ws://" + document.location.host + "/ITRevolution2017/chat/" + issue);


    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    var to = $_GET("num");
    var json = JSON.stringify({
        "to":to,
        "content":content
    });

    ws.send(json);
}