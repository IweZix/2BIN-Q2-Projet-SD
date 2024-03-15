package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

  /**
   * Decimal format for the distance
   */
  private static final DecimalFormat df = new DecimalFormat("#.##");

  private final Map<City, Set<Road>> ListDAdjacence;
  private final Map<String, City> cities;


  /**
   * Constructor
   * @param cities File containing the cities
   * @param roads File containing the roads
   * @throws IOException if an error occurs while reading the files
   */
  Graph(File cities, File roads) throws IOException {
    this.ListDAdjacence = new HashMap<>();
    this.cities = new HashMap<>();

    loadCities(cities);
    loadRoads(roads);
  }

  /**
   * Load the cities from the file
   * @param cities File containing the cities
   * @throws IOException if an error occurs while reading the file
   */
  private void loadCities(File cities) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(cities));
    String line;
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      int id = Integer.parseInt(data[0]);
      String nom = data[1];
      double longitude = Double.parseDouble(data[2]);
      double latitude = Double.parseDouble(data[3]);
      City c = new City(id, nom, longitude, latitude);
      this.ListDAdjacence.put(c, new HashSet<>());
      this.cities.put(nom, c);
    }
  }

  /**
   * Load the roads from the file
   * @param roads File containing the roads
   * @throws IOException if an error occurs while reading the file
   */
  public void loadRoads(File roads) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(roads));
    String line;
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      int e1 = Integer.parseInt(data[0]);
      int e2 = Integer.parseInt(data[1]);
      Road r = new Road(e1, e2);
      Road r2 = new Road(e2, e1);
      for (City c : this.ListDAdjacence.keySet()) {
        if (c.getId() == e1) {
          this.ListDAdjacence.get(c).add(r);
        }
        if (c.getId() == e2) {
          this.ListDAdjacence.get(c).add(r2);
        }
      }
    }
  }

  /**
   * Calculate the route minimizing the number of roads using a DFS
   * @param depart city of departure
   * @param arrivee city of arrival
   */
  public void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee) {
    City departCity = cities.get(depart);
    City arriveeCity = cities.get(arrivee);

    if (departCity == null || arriveeCity == null) {
      System.out.println("City not found.");
      return;
    }

    dfs(departCity, arriveeCity);
  }

  /**
   * deep first search
   * @param departCity city of departure
   * @param arriveeCity city of arrival
   */
  private void dfs(City departCity, City arriveeCity) {
    int nbRoads = 0;
    double nbKm = 0;

    ArrayDeque<City> file = new ArrayDeque<>(); // queue
    Set<City> visited = new HashSet<>(); // visited cities
    Map<City, City> predecesseur = new HashMap<>(); // save the previous city <actual, previous>

    file.add(departCity);
    visited.add(departCity);
    predecesseur.put(departCity, null);

    while (!file.isEmpty()) {
      City actual = file.poll();
      if (actual.equals(arriveeCity)) {
        break;
      }
      if (ListDAdjacence.containsKey(actual)) {
        for (Road r : ListDAdjacence.get(actual)) {
          City voisin = ListDAdjacence.keySet().stream()
              .filter(c -> c.getId() == r.getExtremite2())
              .findFirst().orElse(null);
          if (voisin != null && !visited.contains(voisin)) {
            file.add(voisin);
            visited.add(voisin);
            predecesseur.put(voisin, actual);
          }
        }
      }
    }

    List<City> pathCities = new ArrayList<>();
    List<String> pathDistances = new ArrayList<>();
    City actual = arriveeCity;
    while (actual != null) {
      City previous = predecesseur.get(actual);
      if (previous != null) {
        nbRoads++;
        double km = Util.distance(actual.getLongitute(), actual.getLatitude(), previous.getLongitute(), previous.getLatitude());
        nbKm += km;
        pathCities.add(actual);
        pathDistances.add(df.format(km));
      }
      actual = previous;
    }
    pathCities.add(departCity);

    pathCities = pathCities.reversed();
    pathDistances = pathDistances.reversed();
    printPath(pathCities, pathDistances, nbRoads, nbKm);
  }

  /**
   * Print the path
   * @param pathCities list of cities
   * @param pathDistances list of distances
   * @param nbRoads number of roads
   * @param nbKm number of kilometers
   */
  private void printPath(List<City> pathCities, List<String> pathDistances, int nbRoads, double nbKm) {
    System.out.println("Trajet de " + pathCities.getFirst().getNom() + " à " + pathCities.getLast().getNom() + " : " + nbRoads + " routes et " + nbKm + " km");

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < pathCities.size() - 1; i++) {
      sb.append(pathCities.get(i).getNom())
          .append(" -> ")
          .append(pathCities.get(i + 1).getNom())
          .append(" : ")
          .append("(").append(pathDistances.get(i)).append(")")
          .append(" km\n");
    }
    System.out.println(sb.toString());
  }

  /**
   * Calculate the route minimizing the number of kilometers using Dijkstra's algorithm
   * @param depart city of departure
   * @param arrivee city of arrival
   */
  public void calculerItineraireMinimisantKm(String depart, String arrivee) {
    System.out.println("Calcul de l'itinéraire minimisant le nombre de kilomètres");
    City departCity = cities.get(depart);
    City arriveeCity = cities.get(arrivee);

    if (departCity == null || arriveeCity == null) {
      System.out.println("City not found.");
      return;
    }

    dijkstra(departCity, arriveeCity);
  }

  /**
   * Dijkstra's algorithm
   * @param departCity city of departure
   * @param arriveeCity city of arrival
   */
  private void dijkstra(City departCity, City arriveeCity) {
    // TODO
  }


}

