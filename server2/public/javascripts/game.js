// global variable (todo: change)
var blockSize = 0


/*
 * this function take an array of the following format:
 * {id: 0, fields: [{x: 1, y: 2, type: 0}]}
 * and returns an 2-dimensional array, e.g.
 * [[1,1,1], [2,2,2], [2,2,2]] (3x3 array with ones in first row, twos in second and so on...)
 */
function convertToArray(json) {
  var a = [];

  // first find out the size of the field
  var height = 0;
  var width = 0;
  json["fields"].forEach(function(f) {
    var x = f["x"];
    var y = f["y"];
    if (x>width) width = x
    if (y>height) height = y
  });

  // highest value + 1 => dimension
  height = height + 1;
  width = width + 1;

  // initialize a with zeros
  for (row=0;row<height;row++) {
    a.push(new Array(width));
    for (col=0;col<width;col++) {
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

function setBlockSize(id) {
    var canvas = document.getElementById("myCanvas");
    var cw = canvas.width;
    var ch = canvas.height;
    var ctx = canvas.getContext("2d");

    // get track as json
    $.get("/track/" + id, function(t) {
        track = convertToArray(t);
        var height = track.length
        var width  = track[0].length
        var blockWidth = cw/width;
        var blockHeight = cw/height;
        blockSize = blockWidth < blockHeight ? blockWidth : blockHeight;

        console.log("track height: " + height)
        console.log("track width: "  + width)
        console.log("block size: "   + blockSize)
    });
}

/*
 * draws track (static background) on canvas
 */
function drawBackgroundCanvas(track) {
    var canvas = document.getElementById("myCanvas");
    var ctx = canvas.getContext("2d");

    var height = track.length
    var width  = track[0].length

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

    for(var row=0; row < height; row++) {
      for(var col=0; col < width; col++) {
        draw(row, col, track[row][col]);
      }
    }
}

function drawCar(token) {
    var canvas = document.getElementById("myCanvas");
    var ctx = canvas.getContext("2d");

    function draw(x,y) {
        ctx.fillStyle = 'blue';
        ctx.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
    }

    $.get("/car/" + token, function(c) {
        var x = c["px"]
        var y = c["py"]
        console.log("draw car at pos (x|y): (" + x + "|" + y + ")")
        draw(x,y);
    });
}

function drawBackground(id) {
    $.get("/track/" + id, function(t) {
        track = convertToArray(t);
        drawBackgroundCanvas(track);
    });
}

function moveTestCar() {
    // Post to /move/test with jquery
    $.get("/car/test", function(c) {
        var x = c["px"]
        var y = c["py"]
        $.post("/move/test", {"px": x+1, "py": y})
    });
}

function initialize() {

    // todo: testing...
    var trackId = 0;
    var token   = "test";

    setBlockSize(trackId);
    for (var i=0;i<10000;i++)
    drawBackground(trackId);
    for (var i=0;i<10000;i++)
    drawCar(token);
}

window.onload = initialize

