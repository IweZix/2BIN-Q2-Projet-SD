package src;

import java.util.Objects;

public class Road {

  private City extremite1, extremite2;

  public Road(City extremite1, City extremite2) {
    this.extremite1 = extremite1;
    this.extremite2 = extremite2;
  }

  public City getExtremite1() {
    return extremite1;
  }


  public City getExtremite2() {
    return extremite2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Road road = (Road) o;
    return extremite1 == road.extremite1 && extremite2 == road.extremite2;
  }

  @Override
  public int hashCode() {
    return Objects.hash(extremite1, extremite2);
  }
}
