
/*
 * json example {id: 0, fields: [{x: 1, y: 2, type: 0}]}
 * todo
 */
function convertToArray(json) {
  var a = [];

  // first find out the size of the field
  var maxRow = 0;
  var maxCol = 0;
  json["fields"].forEach(function(f) {
    var x = f["x"];
    var y = f["y"];
    if (x>maxCol) maxCol = x
    if (x>maxRow) maxRow = y
  });

  // initialize a
  for (row=0;row<maxRow;row++) {
    a.push(new Array(maxCol));
    for (col=0;col<maxCol;col++) {
      a[row][col] = 0;
    }
  }

  console.log(a);

  // fill a with proper data
  json["fields"].forEach(function(f) {
    var col = f["x"];
    var row = f["y"];
    var t = f["type"];
    a[row][col] = t;
  });
}


// todo
function drawTrack(id) {
    // get /track/0
    $.get("/track/" + id, function(track) {
        // format: id: Int, wx: List[Int], wy: List[Int], fx: List[Int], fy: List[Int], sx: List[Int], sy: List[Int]
        console.log(track);


    });


    // convert it to an 2d array
    var field = [
                    [1,2,2,3],
                    [0,0,0,0],
                    [1,1,1,1]
                ];

    // draw track
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

    for(var n=0; n<=field.length; n++){
      for(var m=0; m<=field[0].length; m++){
        if(field[n][m]==0){
          racetrack(m, n);
        }
        else if (field[n][m]==1) {
          border(m, n);
        }
        else if (field[n][m]==2) {
          startline(m, n);
        }
        else {
          finishline(m, n);
        }
      }
    }

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

