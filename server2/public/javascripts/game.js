
function drawTrack() {
    // get track

    // draw track

}

function drawTest() {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.moveTo(0,0);
    ctx.lineTo(200,100);
    ctx.stroke();
}


function initialize() {
    drawTest();
}

window.onload = initialize
