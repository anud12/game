package game.library.pawn.order;

import game.engine.IEngineAction;
import game.geom.IVector;
import game.geom.classes.PointF;
import game.geom.classes.Vector;
import game.library.pawn.Pawn;

public class Move extends PawnOrder{

	//Variables
		protected Pawn pawn;
		protected PointF destination;
		
		protected IVector step;
		protected IVector direction;
		
		protected boolean completedPremature;
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
		public IEngineAction execute(double deltaTime) 
		{
			if(direction.getLength() == 0)
				return this;
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
			
			return this;
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


}
