package pl.woroniecki.enuminterface.core.gameobject;

public class Item extends GameObject {

  private final ItemType type;
  private boolean collected;

  public Item(ItemType type, double x, double y) {
    super(x, y);
    this.type = type;
  }

  public void applyTo(Character character) {
    if (collected) {
      throw new IllegalStateException("Item has already been collected - unable to collect it again");
    }
    type.applyTo(character);
    collected = true;
  }

  public ItemType getType() {
    return type;
  }

  public boolean isNotCollected() {
    return !collected;
  }

  @Override
  public String toString() {
    return String.format("Item(type=%s, position=(%.2f, %.2f))", type, getX(), getY());
  }
}
