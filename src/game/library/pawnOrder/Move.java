package game.library.pawnOrder;

import java.awt.geom.Point2D;

import game.engine.IEngineAction;
import game.geom.IVector;
import game.geom.classes.Vector;
import game.library.Pawn;
import game.library.interfaces.IPawnOrder;

public class Move implements IPawnOrder{

	//Variables
		Pawn pawn;
		Point2D.Float destination;
		
		IVector step;
		IVector direction;
		
		boolean completedPremature;
		//Constructors
		
		public Move(Pawn pawn, Point2D.Float destination)
		{
			this.pawn = pawn;
					
			this.destination = destination;
			
			direction = new Vector();
			step = new Vector();
			
			//Calculate the direction
			direction.setX(destination.getX() - pawn.getRectangle().getX());
			direction.setY(destination.getY() - pawn.getRectangle().getY());
			direction.normalize();
		}
		//Setters
		public void setDestination(Point2D.Float destination)
		{			
			this.destination = destination;
			
			//Calculate the direction
			direction.setX(destination.getX() - pawn.getRectangle().getX());
			direction.setY(destination.getY() - pawn.getRectangle().getY());
			direction.normalize();
		}
		
		
		//Functions from IGameLoop
		@Override
		public void execute(double deltaTime) 
		{
			//Calculate the next step to go towards  the destination
			step.equal(direction);
			step.multiplyByScalar(pawn.getMovementSpeed() * deltaTime);
			
			//Check if the step is over the destination
			if(pawn.getCenter().distance(this.destination) < step.getLength())
			{
				//Move directly on top
				pawn.getRectangle().x = destination.x;
				pawn.getRectangle().y = destination.y;
			}
			else
			{
				//Make the step
				pawn.getRectangle().x += step.getX();
				pawn.getRectangle().y += step.getY();
			}
			//Artifact needed to be implemented in Entity
			pawn.setCenter(pawn.getRectangle().x , pawn.getRectangle().y);
			
			
		}

		@Override
		public boolean isCompleted(IEngineAction action) {
			
			if(pawn.getCenter().distance(this.destination) == 0)
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
