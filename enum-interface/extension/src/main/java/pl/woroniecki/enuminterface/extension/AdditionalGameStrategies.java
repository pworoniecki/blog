package pl.woroniecki.enuminterface.extension;

import pl.woroniecki.enuminterface.core.Game;
import pl.woroniecki.enuminterface.core.gameobject.Character;
import pl.woroniecki.enuminterface.core.gameobject.Item;
import pl.woroniecki.enuminterface.core.gameobject.ItemType;
import pl.woroniecki.enuminterface.core.strategy.GameStrategy;

import java.util.Optional;

public enum AdditionalGameStrategies implements GameStrategy {

  COLLECT_HEALTH_POTION_IF_LOW_HP {
    @Override
    public void performAction(Game game, Character character) {
      Optional<Item> healthPotion = getNotCollectedHealthPotion(game);
      if (character.getHealthPoints() < Character.MAX_HEALTH / 2 && healthPotion.isPresent()) {
        System.out.printf("%s has low hp and will try to collect health potion: %s", character, healthPotion.get());
        character.collectOrMoveTowardItem(healthPotion.get());
      } else {
        Character enemy = game.getFirstAliveCharacterOtherThan(character);
        character.attackOrMoveTowardEnemy(enemy);
      }
    }
  },
  COLLECT_MAGIC_POTION_IF_LOW_MANA {
    @Override
    public void performAction(Game game, Character character) {
      Optional<Item> magicPotion = getNotCollectedMagicPotion(game);
      if (character.getMagicPoints() < Character.MAGIC_POINTS_PER_ATTACK && magicPotion.isPresent()) {
        System.out.printf("%s has low mana and will try to collect magic potion: %s\n", character, magicPotion.get());
        character.collectOrMoveTowardItem(magicPotion.get());
      } else {
        Character enemy = game.getFirstAliveCharacterOtherThan(character);
        character.attackOrMoveTowardEnemy(enemy);
      }
    }
  };

  Optional<Item> getNotCollectedHealthPotion(Game game) {
    return getNotCollectedItemOfType(game, ItemType.HEALTH_POTION);
  }

  private Optional<Item> getNotCollectedItemOfType(Game game, ItemType type) {
    return game.getNotCollectedItems().stream().filter(item -> item.getType() == type).findFirst();
  }

  Optional<Item> getNotCollectedMagicPotion(Game game) {
    return getNotCollectedItemOfType(game, ItemType.MAGIC_POTION);
  }
}
