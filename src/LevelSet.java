
public class LevelSet {
	private Level[] levs;
	int current;
	
	public LevelSet(){
		levs = new Level[2];
		current = 0;
		
		levs[0] = new Level();
		Obstacle[] obs = {new Obstacle(400, 150)};
		levs[1] = new Level(obs, new Goal("Bird"), new Goal[0]);
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
