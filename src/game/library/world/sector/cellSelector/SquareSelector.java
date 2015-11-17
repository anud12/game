package game.library.world.sector.cellSelector;

import java.util.Set;

import game.geom.classes.PointI;
import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;

class SquareSelector extends CellSelector
{
	
	
	public SquareSelector(SectorGrid grid) {
		super(grid);
	}
	
	@Override
	public SquareCell returnValue(SquareCell squareCell) {
		return squareCell;
	}
	
	@Override
	public boolean isValid(SquareCell squareCell, Set<Cell> except) {
		if(parentGrid.isSquareFree(squareCell ))
		{
			if(!except.contains(squareCell))
			{
				if(firstPass)
				{
					firstPass = false;
					PointI coordinates = this.parentGrid.getGridCoordinate(squareCell);
					omogenRange = Math.min(Math.abs(coordinates.x), Math.abs(coordinates.y));
				}
				
				return true;
				
			}
		}
		return false;
	}
}
