package src;

import java.util.Objects;

public class City {

  private int id;
  private String nom;
  private double longitute, latitude;

  public City(int id, String nom, double longitute, double latitude) {
    this.id = id;
    this.nom = nom;
    this.longitute = longitute;
    this.latitude = latitude;
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public double getLongitute() {
    return longitute;
  }

  public double getLatitude() {
    return latitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    City citie = (City) o;
    return id == citie.id && longitute == citie.longitute && latitude == citie.latitude
        && Objects.equals(nom, citie.nom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, longitute, latitude);
  }

  @Override
  public String toString() {
    return "Citie{" +
        "id=" + id +
        ", nom='" + nom + '\'' +
        ", longitute=" + longitute +
        ", latitude=" + latitude +
        '}';
  }
}
