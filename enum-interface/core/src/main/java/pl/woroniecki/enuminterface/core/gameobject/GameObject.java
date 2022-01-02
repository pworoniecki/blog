package pl.woroniecki.enuminterface.core.gameobject;

public class GameObject {

  private double x;
  private double y;

  public GameObject(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void setPosition(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getDistanceTo(GameObject gameObject) {
    return Math.sqrt(Math.pow(gameObject.getX() - this.x, 2) + Math.pow(gameObject.getY() - this.y, 2));
  }
}
