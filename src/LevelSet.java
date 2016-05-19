import java.awt.Point;

public class LevelSet {
	private Level[] levs;
	private boolean[] complete;
	private int current;
	private int highest;
	
	public LevelSet(){
		levs = new Level[15];
		complete = new boolean[levs.length];
		current = 0;
		
		levs[0] = new Level(null, new Goal("Bird"), null, 
				"Click and drag the arm" + "\nRelease to shoot"
				+ "\nCatch the bird", false);
		
		levs[1] = new Level(null, new Goal(new Point(425, 300), "Bird"), null,  
				"Rotate the gear to change launch angle"
				+ "\nMove slider to adjust power", false);
		
		Goal[] coins = {new Goal(new Point(425,200),"Fish")};
		levs[2] = new Level(null, new Goal("Bird"), coins, 
				"Catch fish for extra points", false);
		
		Obstacle[] obs = {new Obstacle(350, 275)};
		levs[3] = new Level(obs, new Goal("Bird"), null, "Avoid obstacles", false);
		
		Obstacle[] obs2 = {new Obstacle(350, 150)};
		Goal[] coins2 = {new Goal(new Point(350,75),"Fish")};
		levs[4] = new Level(obs2, new Goal(new Point(630, 300),"Bird"), coins2, null, false);
		
		Obstacle[] obs3 = {new Obstacle(600,100), new Obstacle(600,200), new Obstacle(600, 300)};
		Goal[] coins3 = {new Goal(new Point(380,100), "Fish")};
		levs[5] = new Level(obs3, new Goal(new Point(550, 300),"Bird"), coins3, null, false);
		
		Goal[] coins4 = {new Goal(new Point(250, 100), "Fish"), new Goal(new Point(300, 130), "Fish"), 
				new Goal(new Point(350, 180), "Fish"), new Goal(new Point(400, 225), "Fish")};
		levs[6] = new Level(null, new Goal(new Point(480, 300),"Bird"), coins4, null, false);
	
		MovingGoal mov = new MovingGoal(new Point(0,50), "Bird", true, 5, 600, 0);
		levs[7] = new Level(null, mov, null, null, true);
		
		MovingGoal mov2 = new MovingGoal(new Point(500, 150), "Bird", false, 5, 275, 10);
		Goal[] coins5 = {new Goal(new Point(250, 150), "Fish"), new Goal(new Point(325, 150), "Fish"),
				new Goal(new Point(400, 150), "Fish")};
		levs[8] = new Level(null, mov2, coins5, null, true);
		
		Obstacle[] obs4 = {new Obstacle(300, 200)};
		MovingGoal mov3 = new MovingGoal(new Point(500,10), "Bird", false, 5, 300, 10);
		levs[9] = new Level(obs4, mov3, null, null, true);
		
		Obstacle[] obs5 = {new Obstacle(400,250), new Obstacle(400,10)};
		MovingGoal mov4 = new MovingGoal(new Point(510,10), "Bird", false, 1, 300, 10);
		levs[10] = new Level(obs5, mov4, null, null, true);
		
		Obstacle[] obs6 = {new Obstacle(500,20)};
		levs[11] = new Level(obs6, new Goal(new Point(550,-39), "Bird"), null, null, false);
		
		Obstacle[] obs7 = {new Obstacle(500,250), new Obstacle(500,10), new Obstacle(600,240), new Obstacle(600,20), new Obstacle(700,230), new Obstacle(700,30)};
		levs[12] = new Level(obs7, new Goal(new Point(750, 180), "Bird"), null, null, false);
		
		Obstacle[] obs8 = {new Obstacle(300, 300), new Obstacle(300,230)};
		levs[13] = new Level(obs8, new Goal(new Point(500, 300), "Bird"), null, null, false);
		
		Goal[] coins6 = {new Goal(new Point(300, 175), "Fish"), new Goal(new Point(300, 100), "Fish"), new Goal(new Point(300, 250), "Fish"), new Goal(new Point(300, 25), "Fish"), 
					new Goal(new Point(375, 175), "Fish"), new Goal(new Point(375, 100), "Fish"), new Goal(new Point(375, 250), "Fish"), new Goal(new Point(375, 25), "Fish"), 
					new Goal(new Point(450, 175), "Fish"), new Goal(new Point(450, 100), "Fish"), new Goal(new Point(450, 250), "Fish"), new Goal(new Point(450, 25), "Fish"), 
					new Goal(new Point(525, 175), "Fish"), new Goal(new Point(525, 100), "Fish"), new Goal(new Point(525, 250), "Fish"), new Goal(new Point(525, 25), "Fish")};
		MovingGoal mov5 = new MovingGoal(new Point(600,20), "Bird", false, 1, 270, 10);
		levs[14] = new Level(null, mov5, coins6, null, true);
		
	}
	
	public void nextLevel(){
		complete[current] = true;
		current++;
		if(current>highest)
			highest = current;
	}
	
	public int highestLevel(){
		return highest;
	}
	
	public boolean isComplete(int n){
		return complete[n];
	}
	
	public Level getCurrent(){
		if(current>=levs.length){
			return null;
		}
		Level curLev = levs[current];
		curLev.reset();
		return curLev;
	}
	
	public int getLevelNum(){
		return current+1;
	}
	
	public Level getLevel(int n){
		return levs[n];
	}
	
	public int numLevels(){
		return levs.length;
	}
	
	public void setCurrent(int n){
		current = n-1;
	}
}
