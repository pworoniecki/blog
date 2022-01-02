package pl.woroniecki.enuminterface.extension;

import pl.woroniecki.enuminterface.core.Game;
import pl.woroniecki.enuminterface.core.gameobject.Character;
import pl.woroniecki.enuminterface.core.gameobject.GameObject;
import pl.woroniecki.enuminterface.core.gameobject.Item;
import pl.woroniecki.enuminterface.core.gameobject.ItemType;
import pl.woroniecki.enuminterface.core.strategy.BasicGameStrategies;

import java.util.List;

public class SampleGameRunner {

  public static void main(String[] args) {
    Character player1 = new Character("Player 1", 0, 0, BasicGameStrategies.ONLY_ATTACK_ENEMIES);
    Character player2 = new Character("Player 2", 50, 50, BasicGameStrategies.COLLECT_ITEMS_THEN_ATTACK);
    Character player3 = new Character("Player 3", 70, 70, AdditionalGameStrategies.COLLECT_HEALTH_POTION_IF_LOW_HP);
    Character player4 = new Character("Player 4", 90, 90, AdditionalGameStrategies.COLLECT_MAGIC_POTION_IF_LOW_MANA);
    Item healthPotion1 = new Item(ItemType.HEALTH_POTION, 20, 20);
    Item healthPotion2 = new Item(ItemType.HEALTH_POTION, 40, 40);
    Item healthPotion3 = new Item(ItemType.HEALTH_POTION, 60, 60);
    Item healthPotion4 = new Item(ItemType.HEALTH_POTION, 80, 80);
    Item magicPotion1 = new Item(ItemType.MAGIC_POTION, 25, 25);
    Item magicPotion2 = new Item(ItemType.MAGIC_POTION, 40, 40);
    Item magicPotion3 = new Item(ItemType.MAGIC_POTION, 55, 55);
    Item magicPotion4 = new Item(ItemType.MAGIC_POTION, 70, 70);

    List<GameObject> gameObjects = List.of(player1, player2, player3, player4, healthPotion1, healthPotion2,
        healthPotion3, healthPotion4, magicPotion1, magicPotion2, magicPotion3, magicPotion4);
    Game game = new Game(gameObjects);
    game.run();
  }
}
