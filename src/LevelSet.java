import java.awt.Point;

public class LevelSet {
	private Level[] levs;
	int current;
	
	public LevelSet(){
		levs = new Level[5];
		current = 0;
		
		levs[0] = new Level(null, new Goal("Bird"), null, 
				"Click and drag the arm" + "\nRelease to shoot"
				+ "\nCatch the bird");
		
		levs[1] = new Level(null, new Goal(new Point(425, 300), "Bird"), null,  
				"Rotate the gear to change launch angle"
				+ "\nMove slider to adjust power");
		
		Obstacle[] obs = {new Obstacle(425, 275)};
		levs[2] = new Level(obs, new Goal("Bird"), null, "Avoid obstacles");
		
		
		Goal[] coins = {new Goal(new Point(425,200),"Fish")};
		levs[3] = new Level(null, new Goal("Bird"), coins, 
				"Catch fish for extra points");
		
		Obstacle[] obs2 = {new Obstacle(425, 150)};
		Goal[] coins2 = {new Goal(new Point(450,75),"Fish")};
		levs[4] = new Level(obs2, new Goal("Bird"), coins2, null);
	}
	
	public void nextLevel(){
		current++;
	}
	
	public Level getCurrent(){
		Level curLev = levs[(current)%levs.length];
		curLev.reset();
		return curLev;
	}
	
	public int getLevelNum(){
		return current+1;
	}
}
