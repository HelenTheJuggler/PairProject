
public class LevelSet {
	private Level[] levs;
	int current;
	
	public LevelSet(){
		levs = new Level[2];
		current = 0;
		
		levs[0] = new Level(new Obstacle[0], new Goal("Bird"), new Goal[0], "Drag arm back and release to shoot.");
		Obstacle[] obs = {new Obstacle(400, 150)};
		levs[1] = new Level(obs, new Goal("Bird"), new Goal[0], "Rotate the gear to change launch angle."
				+ "\nMove slider to adjust power");
	}
	
	public Level getCurrent(){
		if(current < levs.length){
			current++;
			return levs[current-1];
		}else{
			return null;
		}
	}
}
