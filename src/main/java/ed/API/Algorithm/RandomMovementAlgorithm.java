package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;

import ed.API.Game.Graph;
import ed.Utils.Map.HashMap;


import java.util.Random;


public class RandomMovementAlgorithm implements Algorithm {

    @Override
    public void move(Bot bot, Graph graph) {
        EntitiesLocation currentLocation = bot.getLocation();

        //System.out.println("passou aqui");
        // Perform random movement
        HashMap<Integer, EntitiesLocation> existingLocations = graph.getLocations();


        // Perform random movement within existing locations
        /*
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        EntitiesLocation newLocation = existingLocations.get(randomNumber);
        */

        bot.setLocation(graph.getLocations().get(9));

    }
}