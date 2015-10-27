package game.library.world.sector.cellSelector;

import java.util.Set;

import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;

class RightTriangleSelector extends CellSelector {

	public RightTriangleSelector(SectorGrid grid) {
		super(grid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cell returnValue(SquareCell squareCell) {
		return squareCell;
		
	}
	@Override
	public boolean isValid(SquareCell squareCell, Set<Cell> except) {
		if(parentGrid.isTriangleFree(squareCell.getRightTriangle()) && !except.contains(squareCell))
			return true;
		return false;
	}

}
