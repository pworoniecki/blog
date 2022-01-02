package pl.woroniecki.enuminterface.core.gameobject;

import pl.woroniecki.enuminterface.core.Game;
import pl.woroniecki.enuminterface.core.strategy.GameStrategy;

public class Character extends GameObject {

  public static final int MAX_HEALTH = 100;
  public static final int MAX_MAGIC = 30;
  public static final int MAGIC_POINTS_PER_ATTACK = 10;

  private static final int MAX_VELOCITY = 10;
  private static final int SWORD_RANGE = 1;
  private static final int SWORD_DAMAGE = 10;
  private static final int MAGIC_RANGE = 2;
  private static final int MAGIC_DAMAGE = 20;
  private static final double MAX_DISTANCE_FROM_ITEM_TO_COLLECT = 0.5;

  private final String name;
  private final GameStrategy gameStrategy;

  private double angle = 0;
  private int healthPoints = MAX_HEALTH;
  private int magicPoints = MAX_MAGIC;

  public Character(String name, double x, double y, GameStrategy gameStrategy) {
    super(x, y);
    this.name = name;
    this.gameStrategy = gameStrategy;
  }

  public void moveToward(GameObject gameObject) {
    rotateToward(gameObject);
    moveBy(Math.min(getDistanceTo(gameObject), MAX_VELOCITY));
  }

  public void rotateToward(GameObject gameObject) {
    double angle = Math.atan2(gameObject.getY() - this.getY(), gameObject.getX() - this.getX());
    if (angle != this.angle) {
      System.out.printf("%1s rotated toward %s\n", this, gameObject);
    }
    setAngle(angle);
  }

  public void moveBy(double distance) {
    if (distance == 0) {
      return;
    }

    double newX = distance * Math.cos(angle) + getX();
    double newY = distance * Math.sin(angle) + getY();
    this.setPosition(newX, newY);
    System.out.printf("%1s moved by %2$,.2f (angle: %3$,.2f)\n", this, distance, angle);
  }

  public void attackOrMoveTowardEnemy(Character enemy) {
    if (canAttackEnemyUsingMagic(enemy)) {
      attackEnemyUsingMagic(enemy);
    } else if (isEnemyInSwordRange(enemy)) {
      attackEnemyUsingSword(enemy);
    } else {
      moveToward(enemy);
    }
  }

  public boolean isEnemyInSwordRange(Character enemy) {
    return getDistanceTo(enemy) <= SWORD_RANGE;
  }

  public void attackEnemyUsingSword(Character enemy) {
    if (!isEnemyInSwordRange(enemy)) {
      throw new IllegalStateException("Enemy is too far to be attacked using sword!");
    }
    enemy.decreaseHealthPointsAfterSwordAttack();
    System.out.printf("%s attacked %s using sword and decreased its health to %d\n", this, enemy,
        enemy.getHealthPoints());
  }

  public boolean canAttackEnemyUsingMagic(Character enemy) {
    return isEnemyInMagicRange(enemy) && hasMagicPointsForAttack();
  }

  public boolean isEnemyInMagicRange(Character enemy) {
    return getDistanceTo(enemy) <= MAGIC_RANGE;
  }

  public boolean hasMagicPointsForAttack() {
    return magicPoints >= MAGIC_POINTS_PER_ATTACK;
  }

  public void attackEnemyUsingMagic(Character enemy) {
    if (!isEnemyInMagicRange(enemy)) {
      throw new IllegalStateException("Enemy is too far to be attacked using magic!");
    }
    if (!hasMagicPointsForAttack()) {
      throw new IllegalStateException("Too little magic points to perform magic attack!");
    }
    enemy.decreaseHealthPointsAfterMagicAttack();
    magicPoints -= MAGIC_POINTS_PER_ATTACK;
    System.out.printf("%s attacked %s using magic and decreased its health to %d\n", this, enemy,
        enemy.getHealthPoints());
  }

  public void collectOrMoveTowardItem(Item item) {
    if (canCollectItem(item)) {
      collectItem(item);
    } else {
      moveToward(item);
    }
  }

  public boolean canCollectItem(Item item) {
    return item.isNotCollected() && getDistanceTo(item) <= MAX_DISTANCE_FROM_ITEM_TO_COLLECT;
  }

  public void collectItem(Item item) {
    if (!canCollectItem(item)) {
      throw new IllegalStateException("Unable to collect item: " + item);
    }
    item.applyTo(this);
    System.out.printf("%s collected %s\n", this, item);
  }

  public String getName() {
    return name;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public int getHealthPoints() {
    return healthPoints;
  }

  public void setHealthPoints(int healthPoints) {
    this.healthPoints = healthPoints;
  }

  public int getMagicPoints() {
    return magicPoints;
  }

  public void setMagicPoints(int magicPoints) {
    this.magicPoints = magicPoints;
  }

  private void decreaseHealthPointsAfterSwordAttack() {
    healthPoints -= SWORD_DAMAGE;
  }

  private void decreaseHealthPointsAfterMagicAttack() {
    healthPoints -= MAGIC_DAMAGE;
  }

  public void performGameAction(Game game) {
    gameStrategy.performAction(game, this);
  }

  @Override
  public String toString() {
    return String.format("Character(%s)", getName());
  }
}
