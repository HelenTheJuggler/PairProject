
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Level{
	private Obstacle[] obs;
	private Goal goal;
	
	public Level(Obstacle[] o, Goal g){
		obs = o;
		goal = g;
	}
	
	public Level(){
		obs = new Obstacle[0];
		goal = new Goal();
	}
	
	public  Obstacle[] getObstacles(){
		return obs;
	}
	
	public Goal getGoal(){
		return goal;
	}
	
	public void achieved(boolean bool){
		
	}
}