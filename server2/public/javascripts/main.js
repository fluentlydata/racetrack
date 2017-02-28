if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}


(function() {
  $(function() {
    return $.get("/player", function(players) {
      return $.each(players, function(index, player) {
        return $("#players").append($("<li>").text(player.name));
      });
    });
  });

}).call(this);


(function() {
  $(function() {
    return $.get("/car", function(cars) {
      return $.each(cars, function(index, car) {
        return $("#cars").append(
            $("<li>").text(car.name + " at pos: (" + car.px + "," + car.py + ") and with velocity: (" + car.vx + "," + car.vy + ")")
        );
      });
    });
  });

}).call(this);

