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

  private final HashMap<City, Set<Road>> cities;

  DecimalFormat df = new DecimalFormat("#.##");

  Graph(File cities, File roads) throws IOException {
    this.cities = new HashMap<>();

    loadCities(cities);
    loadRoads(roads);
  }

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
      this.cities.put(c, new HashSet<>());
    }
  }

  public void loadRoads(File roads) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(roads));
    String line;
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      int e1 = Integer.parseInt(data[0]);
      int e2 = Integer.parseInt(data[1]);
      Road r = new Road(e1, e2);
      Road r2 = new Road(e2, e1);
      for (City c : this.cities.keySet()) {
        if (c.getId() == e1) {
          this.cities.get(c).add(r);
        }
        if (c.getId() == e2) {
          this.cities.get(c).add(r2);
        }
      }
    }
  }


  public void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee) {
    City departCity = cities.keySet().stream()
        .filter(c -> c.getNom().equals(depart))
        .findFirst().orElse(null);
    City arriveeCity = cities.keySet().stream()
        .filter(c -> c.getNom().equals(arrivee))
        .findFirst().orElse(null);

    if (departCity == null || arriveeCity == null) {
      System.out.println("City not found.");
      return;
    }

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
      if (cities.containsKey(actual)) {
        for (Road r : cities.get(actual)) {
          City voisin = cities.keySet().stream()
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

    System.out.println("Trajet de " + depart + " à " + arrivee + " : " + nbRoads + " routes et " + nbKm + " km");

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

  public void calculerItineraireMinimisantKm(String depart, String arrivee) {

  }


}

