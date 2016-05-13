import java.awt.Point;

public class LevelSet {
	private Level[] levs;
	int current;
	
	public LevelSet(){
		levs = new Level[3];
		current = 0;
		
		levs[0] = new Level(new Obstacle[0], new Goal("Bird"), new Goal[0], "Drag arm back and release to shoot.");
		Obstacle[] obs = {new Obstacle(400, 150)};
		levs[1] = new Level(obs, new Goal("Bird"), new Goal[0], "Rotate the gear to change launch angle."
				+ "\nMove slider to adjust power");
		Goal[] coins = {new Goal(new Point(300,100),"Fish")};
		levs[2] = new Level(new Obstacle[0], new Goal("Bird"), coins, "");
	}
	
	public void nextLevel(){
		current++;
	}
	
	public Level getCurrent(){
		Level curLev = levs[(current)%levs.length];
		curLev.reset();
		return curLev;
	}
}
