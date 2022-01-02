package pl.woroniecki.enuminterface.core.gameobject;

public enum ItemType {

  HEALTH_POTION {
    @Override
    public void applyTo(Character character) {
      character.setHealthPoints(Character.MAX_HEALTH);
    }
  },
  MAGIC_POTION {
    @Override
    public void applyTo(Character character) {
      character.setMagicPoints(Character.MAX_MAGIC);
    }
  };

  public abstract void applyTo(Character character);
}
