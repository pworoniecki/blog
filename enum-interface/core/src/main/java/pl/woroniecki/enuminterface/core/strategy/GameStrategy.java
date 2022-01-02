package pl.woroniecki.enuminterface.core.strategy;

import pl.woroniecki.enuminterface.core.gameobject.Character;
import pl.woroniecki.enuminterface.core.Game;

public interface GameStrategy {

  void performAction(Game game, Character character);
}
