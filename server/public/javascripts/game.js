// global variable (todo: change)
var blockSize = 0
var canvas;
var canvasHeight, canvasWidth;
var trackWidth, trackHeight;
var ctx;

var trackId = 0;
var token   = "test";
var track = [];

var carx = -1;
var cary = -1;

function connectToWS(trackId, token) {
    var ws = new WebSocket("ws://localhost:9000/register");

    ws.onopen = function(evt) {
        console.log("connected to websocket server");
    };

    ws.onmessage = function(event) {
        $.get("/car/test", function(c) {
            var x = c["px"]
            var y = c["py"]

            // only update if car pos changed
            if (carx !== x || cary !== y) {

                // redraw everything with new posiiton
                drawBackground();
                drawCar(c);

                // update current coordinates
                carx = x;
                cary = y;
            }
        });
    }

    ws.onerror = function(evt) {
        console.log("Error!");
    };
}

function setGlobalTrackWidthHeight(jsonTrack) {
    // first find out the size of the field
    var height = 0;
    var width = 0;
    jsonTrack["fields"].forEach(function(f) {
        var x = f["x"];
        var y = f["y"];
        if (x>width) width = x
        if (y>height) height = y
    });

    // highest value + 1 => dimension
    trackHeight = height + 1;
    trackWidth  = width + 1;
}

/*
 * this function take an array of the following format:
 * {id: 0, fields: [{x: 1, y: 2, type: 0}]}
 * and returns an 2-dimensional array, e.g.
 * [[1,1,1], [2,2,2], [2,2,2]] (3x3 array with ones in first row, twos in second and so on...)
 */
function convertToArray(json) {
  var a = [];

  // initialize a with zeros
  for (row = 0; row < trackHeight; row++) {
    a.push(new Array(trackWidth));
    for (col = 0; col < trackWidth; col++) {
      a[row][col] = 0;
    }
  }

  // fill a with correct type data
  json["fields"].forEach(function(f) {
    console.log(f)
    var col = f["x"];
    var row = f["y"];
    a[row][col] = f["t"];
  });

  return a;
}

function setGlobalBlockSize() {
    var blockWidth = canvasWidth/trackWidth;
    var blockHeight = canvasHeight/trackHeight;
    blockSize = blockWidth < blockHeight ? blockWidth : blockHeight;
}


/*
 * draws track (static background) on canvas
 */
function drawBackground() {
    var styleMap = {
        0: '#e5e5e5', // race floor
        1: '#c6c6c6', // border/wall
        2: '#e0ffe7', // start line
        3: '#ffe0e0'  // finish line
    }

    function draw(x,y,t) {
          ctx.fillStyle = styleMap[t];
          ctx.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
          ctx.strokeRect(x*blockSize, y*blockSize, blockSize, blockSize);
    }

    for(var row=0; row < trackHeight; row++) {
      for(var col=0; col < trackWidth; col++) {
        draw(row, col, track[row][col]);
      }
    }
}

function drawCar(car) {
    var x = car["px"]
    var y = car["py"]
    ctx.fillStyle = 'blue';
    ctx.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
}


function moveTestCarForward() {
     // Post to /move/test with jquery
     $.get("/car/test", function(c) {
         var x = c["px"]
         var y = c["py"]
         $.post("/move/test", {"px": x+1, "py": y})
     });
}

function initialize() {

    canvas = document.getElementById("myCanvas");
    ctx = canvas.getContext("2d");
    canvasHeight = canvas.height;
    canvasWidth = canvas.width;

    // todo: testing...
    trackId = 0;
    token   = "test";

    connectToWS(trackId, token);

    // async tasks
    var deferredTrack = $.get("/track/0");
    var deferredCar   = $.get("/car/test");

    $.when( deferredTrack, deferredCar )
     .done(
         // called, if all async tasks are done
         function(t, c) {

             // get returns an array where the first element is the data returned by the server
             var jsonTrack = t[0];
             var jsonCar   = c[0];

             console.log(jsonTrack)
             console.log(jsonCar)

             // this sets trackHeight, trackWidth
             setGlobalTrackWidthHeight(jsonTrack);

             // track is a 2d-array. also a global variable..
             track = convertToArray(jsonTrack);
             console.log(track)

             setGlobalBlockSize();
             console.log(blockSize)

             drawBackground();

             drawCar(jsonCar);
         }
     )
 }

window.onload = initialize

