package src;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		try {
			File cities = new File("cities.txt");
			File roads = new File("roads.txt");
			Graph graph = new Graph(cities, roads);
			graph.calculerItineraireMinimisantNombreRoutes("Berlin", "Madrid");
			System.out.println("--------------------------");
			graph.calculerItineraireMinimisantKm("Berlin", "Madrid");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
