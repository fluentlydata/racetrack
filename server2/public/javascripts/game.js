
/*
 * json example {id: 0, fields: [{x: 1, y: 2, type: 0}]}
 * todo
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
  console.log("height: " + height)
  console.log("width: " + width)

  // initialize a
  for (row=0;row<height;row++) {
    a.push(new Array(width));
    for (col=0;col<width;col++) {
      a[row][col] = 0;
    }
  }

  console.log("after zero-initialization: ");
  console.log(a);

  // fill a with proper data
  json["fields"].forEach(function(f) {
    console.log(f)
    var col = f["x"];
    var row = f["y"];
    a[row][col] = f["t"];
  });

  return a;
}


function drawCanvas(track) {
    var c=document.getElementById("myCanvas");
    var ctx=c.getContext("2d");
    var block = 20;

    var border = function(x,y){
      ctx.fillStyle = '#c6c6c6';
      ctx.fillRect(x*block, y*block, block, block);
      ctx.strokeRect(x*block, y*block, block, block);
      ctx.strokeStyle = "#999";
    }

    var racetrack = function(x,y){
      ctx.fillStyle = '#e5e5e5';
      ctx.fillRect(x*block, y*block, block, block);
      ctx.strokeRect(x*block, y*block, block, block);
    }

    var startline = function(x,y){
      ctx.fillStyle = '#e0ffe7';
      ctx.fillRect(x*block, y*block, block, block);
      ctx.strokeRect(x*block, y*block, block, block);
    }

    var finishline = function(x,y){
      ctx.fillStyle = '#ffe0e0';
      ctx.fillRect(x*block, y*block, block, block);
      ctx.strokeRect(x*block, y*block, block, block);
    }

    var height = track.length
    var width  = track[0].length
    console.log("track height: " + height)
    console.log("track width: " + width)


    for(var row=0; row < height; row++){
      for(var col=0; col < width; col++){

        console.log("(track["+row+"]["+col+"])" + track[row][col])

        if(track[row][col]==0){
          racetrack(col, row);
        }
        else if (track[row][col]==1) {
          border(col, row);
        }
        else if (track[row][col]==2) {
          startline(col, row);
        }
        else {
          finishline(col, row);
        }
      }
    }
}

function drawTrack(id) {

    // get /track/0
    $.get("/track/" + id, function(t) {
        // format: id: Int, wx: List[Int], wy: List[Int], fx: List[Int], fy: List[Int], sx: List[Int], sy: List[Int]
        console.log(t);
        track = convertToArray(t);
        console.log(track);
        drawCanvas(track);
    });
}

function drawTest() {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.moveTo(0,0);
    ctx.lineTo(200,100);
    ctx.stroke();
}

function initialize() {

    // drawTest();
    drawTrack(0);
}

window.onload = initialize

