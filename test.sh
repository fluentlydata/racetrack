# add lukas and patrick
printf "adding Patrick and Lukas\n"
patrick=$(curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: a56df9f1-3d4e-0815-e73a-6856fdd0e554" -d '{
	"name": "Patrick"
}' "http://localhost:8080/player" | python3 -c "import sys, json; print(json.load(sys.stdin)['token'])")

printf "$patrick\n"

lukas=$(curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: a56df9f1-3d4e-0815-e73a-6856fdd0e554" -d '{
    "name": "Lukas"
}' "http://localhost:8080/player" | python3 -c "import sys, json; print(json.load(sys.stdin)['token'])")

printf "$lukas\n"


# adding a dummy track
printf "\nadding a dummy track\n"
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 359348f9-39e4-d9fb-8759-871c31cd86fa" -d '{
    "wall" : [
        {"x": 2, "y": 0},
        {"x": 2, "y": 1},
        {"x": 2, "y": 2},
        {"x": 2, "y": 3},
        {"x": 2, "y": 4},
        {"x": 2, "y": 5},
        {"x": -2, "y": 0},
        {"x": -2, "y": 1},
        {"x": -2, "y": 2},
        {"x": -2, "y": 3},
        {"x": -2, "y": 4},
        {"x": -2, "y": 5}
    ],
    "start": [
        {"x":-1,"y":0},
        {"x":0,"y":0},
        {"x":1,"y":0}
    ],
    "finish": [
        {"x":-1,"y":5},
        {"x":0,"y":5},
        {"x":1,"y":5}
    ]
}' "http://localhost:8080/track"


# start game
printf "\nstarting a game with P, L and the dummy track (id 0)\n"
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: fe715c9d-2ea8-5c50-3535-b9e7361f24e6" -d '{
	"player": [ "$lukas", "$patrick" ],
	"track": 0
}' "http://localhost:8080/start"


# move the car of lukas
printf "\nmoving the car of Lukas to (1,1)\n"
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 20c866ee-32e8-eaed-40a6-d674fa7e2c88" -d '{
	"x": 1,
	"y": 1
}' 'http://localhost:8080/car/move/$lukas'



