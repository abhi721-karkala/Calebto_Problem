package com.demo;
import java.util.*;


class City {
    String name;
    int visitCost;
    int reward;

    City(String name, int visitCost, int reward) {
        this.name = name;
        this.visitCost = visitCost;
        this.reward = reward;
    }
}


//DTO to describe travel between cities
class Edge {
    String to;
    int travelCost;

    Edge(String to, int travelCost) {
        this.to = to;
        this.travelCost = travelCost;
    }
}


class Node implements Comparable<Node> {
    String cityName;
    int totalCost;
    int totalReward;
    List<String> path; // To store the path

    Node(String cityName, int totalCost, int totalReward, List<String> path) {
        this.cityName = cityName;
        this.totalCost = totalCost;
        this.totalReward = totalReward;
        this.path = new ArrayList<>(path);
        this.path.add(cityName);
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.totalCost, other.totalCost);
    }
}

public class Calebto{

    public static void main(String[] args) {

        //City details
        Map<String, City> cities = new HashMap<>();
        cities.put("CITY_A", new City("CITY_A", 300, 1000));
        cities.put("CITY_B", new City("CITY_B", 500, 900));
        cities.put("CITY_C", new City("CITY_C", 250, 1500));
        cities.put("CITY_D", new City("CITY_D", 100, 600));

        //City travel details
        Map<String, List<Edge>> graph = new HashMap<>();
        graph.put("CITY_A", Arrays.asList(new Edge("CITY_D", 300)));
        graph.put("CITY_B", Arrays.asList(new Edge("CITY_C", 500), new Edge("CITY_A", 250)));
        graph.put("CITY_C", Arrays.asList(new Edge("CITY_B", 500), new Edge("CITY_D", 250)));
        graph.put("CITY_D", Arrays.asList(new Edge("CITY_C", 250), new Edge("CITY_A", 300), new Edge("CITY_B", 250)));

        String origin = "CITY_A";
        String destination = "CITY_B";
        int pointsAvailable = 2000;

        String result = findBestPlan(cities, graph, origin, destination, pointsAvailable);
        System.out.println(result);
    }

    //Using Djikstras Algorithm to find the best path
    public static String findBestPlan(Map<String, City> cities, Map<String, List<Edge>> graph,
                                      String origin, String destination, int pointsAvailable) {

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(origin, cities.get(origin).visitCost, cities.get(origin).reward, new ArrayList<>()));

        Map<String, Integer> bestCost = new HashMap<>();
        Map<String, Integer> bestReward = new HashMap<>();
        for (String city : cities.keySet()) {
            bestCost.put(city, Integer.MAX_VALUE);
            bestReward.put(city, 0);
        }
        bestCost.put(origin, cities.get(origin).visitCost);
        bestReward.put(origin, cities.get(origin).reward);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.cityName.equals(destination) && current.totalCost <= pointsAvailable) {
                return "\nRoute: " + String.join(" → ", current.path) +
                        "\nReward: " + current.totalReward +
                        "\nCost: " + current.totalCost;
            }

            if (current.totalCost > pointsAvailable) continue;

            if (graph.containsKey(current.cityName)) {
                for (Edge edge : graph.get(current.cityName)) {
                    City nextCity = cities.get(edge.to);

                    int newCost = current.totalCost + edge.travelCost + nextCity.visitCost;
                    int newReward = current.totalReward + nextCity.reward;

                    if (newCost <= pointsAvailable &&
                            (newCost < bestCost.get(edge.to) || newReward > bestReward.get(edge.to))) {
                        bestCost.put(edge.to, newCost);
                        bestReward.put(edge.to, newReward);
                        pq.offer(new Node(edge.to, newCost, newReward, current.path));
                    }
                }
            }
        }

        return "No plan available for this budget.";
    }
}
