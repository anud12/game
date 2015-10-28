package game.library.world.sector.cellSelector;

import java.util.Set;

import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;

class BottomTriangleSelector extends CellSelector {

	public BottomTriangleSelector(SectorGrid grid) {
		super(grid);
	}
	@Override
	public SquareCell returnValue(SquareCell squareCell) {
		return squareCell;
		
	}
	@Override
	public boolean isValid(SquareCell squareCell, Set<Cell> except) {
		if(parentGrid.isTriangleFree(squareCell.getBottomTriangle()))
		{
			if(!except.contains(squareCell))
			{
				return true;
			}
			else
			{
				this.hasSkipped = true;
			}
		}
		return false;
	}
}
