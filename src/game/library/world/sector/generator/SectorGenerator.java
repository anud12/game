package game.library.world.sector.generator;

import game.geom.classes.PointF;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;

public interface SectorGenerator
{
	 public Sector generateFromCenter(SectorGrid grid);
	 public Sector generateAtPoint(SectorGrid grid, PointF point);
}
