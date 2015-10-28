package game.library.pawnOrder;

import java.awt.geom.Point2D;

import game.engine.IEngineAction;
import game.geom.IVector;
import game.geom.classes.PointF;
import game.geom.classes.Vector;
import game.library.Pawn;
import game.library.interfaces.IPawnOrder;

public class Move implements IPawnOrder{

	//Variables
		Pawn pawn;
		PointF destination;
		
		IVector step;
		IVector direction;
		
		boolean completedPremature;
		//Constructors
		
		public Move(Pawn pawn, PointF destination)
		{
			this.pawn = pawn;
			
			direction = new Vector();
			step = new Vector();
			
			setDestination(destination);
		}
		//Setters
		public void setDestination(PointF destination)
		{		
			
			if(pawn.getCenter() != destination)
			{
				this.destination = destination;
				//Calculate the direction
				direction.setX(destination.getX() - pawn.getCenter().getX());
				direction.setY(destination.getY() - pawn.getCenter().getY());
				direction.normalize();
			}
			else
			{
				
				direction.setX(0);
				direction.setY(0);
				System.out.println("NAN");
			}
			
		}
		
		
		//Functions from IGameLoop
		@Override
		public void execute(double deltaTime) 
		{
			if(direction.getLength() == 0)
				return;
			//Calculate the next step to go towards  the destination
			step.equal(direction);
			step.multiplyByScalar(pawn.getMovementSpeed() * deltaTime);
			
			//Check if the step is over the destination
			if(pawn.getCenter().distance(this.destination) < step.getLength())
			{
				pawn.getRectangle().setLocation(destination);
				
			}
			else
			{
				//Make the step
				pawn.getRectangle().setLocation(pawn.getCenter().x + step.getX(), pawn.getCenter().y + step.getY());
			}
		}

		@Override
		public boolean isCompleted(IEngineAction action) {
			
			if(pawn.getCenter().distance(this.destination) == 0 || direction.getLength() == 0)
				return true;
			return false;
		}

		@Override
		public void onComplete(IEngineAction action) {
			
		}

		@Override
		public boolean isRemovable(IEngineAction action) {
			return true;
		}

}
