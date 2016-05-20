package game.library.entity.update;

import java.util.Set;

import game.engine.actions.IEngineRelatedUpdate;
import game.library.entity.Entity;
import game.library.entity.IUseEntity;
import game.library.player.Player;

public abstract class Vision
{
	public abstract float getRadius();
	public abstract void relatedUpdate(Entity entity);
	public abstract Set<Player> getPlayers();
}
