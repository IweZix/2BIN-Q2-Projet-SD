package src;

import java.io.File;
import java.util.*;

public class Graph {

  public File cities;
  public File roads;
  public ArrayList<Cities> listCities = new ArrayList<Cities>();
  public HashMap<String, ArrayList<Roads>> listRoads = new HashMap<String, ArrayList<Roads>>();

  Graph(File cities, File roads) {

    this.cities = cities;
    this.roads = roads;
    readCities();
    readRoads();
  }

  private void readCities() {
    try {
      Scanner scanner = new Scanner(cities);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double latitude = Double.parseDouble(parts[2]);
        double longitude = Double.parseDouble(parts[3]);
        listCities.add(new Cities(id, name, latitude, longitude));
      }
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readRoads() {
    try {
      Scanner scanner = new Scanner(roads);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        int villeDepart = Integer.parseInt(parts[0]);
        int villeArrivee = Integer.parseInt(parts[1]);
        Roads road = new Roads(villeDepart, villeArrivee);
        Roads road2 = new Roads(villeArrivee, villeDepart);
        if (listRoads.containsKey(listCities.get(villeDepart).name)) {
          listRoads.get(listCities.get(villeDepart).name).add(road);
        } else {
          ArrayList<Roads> list = new ArrayList<Roads>();
          list.add(road);
          listRoads.put(listCities.get(villeDepart).name, list);
        }

        if (listRoads.containsKey(listCities.get(villeArrivee).name)) {
          listRoads.get(listCities.get(villeArrivee).name).add(road2);
        } else {
          ArrayList<Roads> list = new ArrayList<Roads>();
          list.add(road2);
          listRoads.put(listCities.get(villeArrivee).name, list);
        }
      }
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee) {
    String villeDepart = depart;
    String villeArrivee = arrivee;
    int nbRoutes = 0;
    int nbKm = 0;

    //file des somments
    ArrayDeque<String> file = new ArrayDeque<String>();

    //liste des sommets visités
    HashSet<String> visite = new HashSet<String>();

    //retient le sommet précédent pour chaque sommet
    Map<String, String> predecesseur = new HashMap<String, String>();

    //ajout du sommet de départ
    file.add(villeDepart);
    visite.add(villeDepart);
    predecesseur.put(villeDepart, null);

    //tant que la file n'est pas vide on cherche le chemin le plus court avec le DFS

    while (!file.isEmpty()) {
      String sommet = file.poll();
      if (sommet.equals(villeArrivee)) {
        break;
      }
      if (listRoads.containsKey(sommet)) {
        for (Roads road : listRoads.get(sommet)) {
          String voisin = listCities.get(road.villeArrivee).name;
          if (!visite.contains(voisin)) {
            visite.add(voisin);
            file.add(voisin);
            predecesseur.put(voisin, sommet);
          }
        }
      }
    }

    List<String> chemin = new ArrayList<>();
    chemin.add(arrivee);
    String ville1 = predecesseur.get(arrivee);
    String ville2 = predecesseur.get(arrivee);
    while (ville1 != null) {
      chemin.add(ville1);
      ville2 = predecesseur.get(ville1);
      
    }

    System.out.println("Trajet de " + villeDepart + " à " + villeArrivee + " :" + nbRoutes + " routes" + " et " + nbKm + " km");


  }

  public void calculerItineraireMinimisantKm(String depart, String arrivee) {
  }
}
