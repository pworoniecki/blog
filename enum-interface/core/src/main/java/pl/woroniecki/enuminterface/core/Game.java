package pl.woroniecki.enuminterface.core;

import pl.woroniecki.enuminterface.core.gameobject.Character;
import pl.woroniecki.enuminterface.core.gameobject.GameObject;
import pl.woroniecki.enuminterface.core.gameobject.Item;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Game(List<GameObject> gameObjects) {

  public void run() {
    while (isMoreThanOneCharacterAlive()) {
      List<Character> aliveCharacters = gameObjects.stream().filter(isAliveCharacter())
          .map(gameObject -> (Character) gameObject)
          .toList();
      aliveCharacters.forEach(character -> character.performGameAction(this));
    }
    System.out.printf("Game has been ended! Winner: %s\n", getFirstAliveCharacter());
  }

  public boolean hasAnyItemsNotCollected() {
    return gameObjects.stream().filter(isNotCollectedItem()).count() > 1;
  }

  private Predicate<GameObject> isNotCollectedItem() {
    return gameObject -> gameObject instanceof Item && ((Item) gameObject).isNotCollected();
  }

  public List<Item> getNotCollectedItems() {
    return gameObjects.stream().filter(isNotCollectedItem())
        .map(gameObject -> (Item) gameObject)
        .collect(Collectors.toList());
  }

  public Item getFirstNotCollectedItem() {
    return getNotCollectedItems().stream().findFirst().orElseThrow(() -> new IllegalStateException("No item in game"));
  }

  public boolean isMoreThanOneCharacterAlive() {
    return gameObjects.stream().filter(isAliveCharacter()).count() > 1;
  }

  private Predicate<GameObject> isAliveCharacter() {
    return gameObject -> gameObject instanceof Character && ((Character) gameObject).getHealthPoints() > 0;
  }

  public Character getFirstAliveCharacterOtherThan(Character character) {
    return gameObjects.stream().filter(gameObject -> isAliveCharacter().test(gameObject) && gameObject != character)
        .findFirst()
        .map(gameObject -> (Character) gameObject)
        .orElseThrow(() -> new IllegalStateException("No other alive characters in game"));
  }

  public Character getFirstAliveCharacter() {
    return gameObjects.stream().filter(isAliveCharacter())
        .findFirst()
        .map(gameObject -> (Character) gameObject)
        .orElseThrow(() -> new IllegalStateException("No alive character in game"));
  }
}
