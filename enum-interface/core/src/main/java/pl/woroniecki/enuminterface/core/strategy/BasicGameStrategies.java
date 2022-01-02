package pl.woroniecki.enuminterface.core.strategy;

import pl.woroniecki.enuminterface.core.gameobject.Character;
import pl.woroniecki.enuminterface.core.Game;
import pl.woroniecki.enuminterface.core.gameobject.Item;

public enum BasicGameStrategies implements GameStrategy {

  ONLY_ATTACK_ENEMIES {
    @Override
    public void performAction(Game game, Character character) {
      Character enemy = game.getFirstAliveCharacterOtherThan(character);
      character.attackOrMoveTowardEnemy(enemy);
    }
  },
  COLLECT_ITEMS_THEN_ATTACK {
    @Override
    public void performAction(Game game, Character character) {
      Character enemy = game.getFirstAliveCharacterOtherThan(character);

      if (game.hasAnyItemsNotCollected()) {
        Item item = game.getFirstNotCollectedItem();
        character.collectOrMoveTowardItem(item);
      } else {
        character.attackOrMoveTowardEnemy(enemy);
      }
    }
  }
}
