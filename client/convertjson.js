function readTextFile(file, callback)
{
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;
                callback(allText);
            }
        }
    }
    rawFile.send(null);
}

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

var t = {}

// read json
readTextFile("/Users/patrick/dev/racetrack/client/track1.json", function(text) {
  t = JSON.parse(text);
  console.log(t);

  var a = convertToArray(t);
  console.log(a);
});


// ganze Welt immer ohne Player
var track = [][] ...

var updateHistory = [
  '{"name": "Patrick", "x": 1, "y": 1}',
  '{"name": "Patrick", "x": 2, "y": 1}',
  '{"name": "Patrick", "x": 3, "y": 1}',
  '{"name": "Patrick", "x": 4, "y": 1}',
  '{"name": "Patrick", "x": 5, "y": 1}',
  '{"name": "Patrick", "x": 6, "y": 1}'
  ...
];

function loadUpdateMessage() {
  // get next updateHistory element

  // associate player name with color or image or whatnot



  // copy the world and draw at the pos (x,y) the car of Patrick, ...

  // jsonToCavas(copyWorld)
}


function jsonToCanvas(jsonString) {
  // parse string
  t = JSON.parse(text);

  // json to array
  convertToArray....


  // array to canvas

}
