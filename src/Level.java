//comment

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Level{
	private Obstacle[] obs;
	private Goal goal;
	private Goal[] coins;
	
	public Level(Obstacle[] o, Goal g, Goal[] c){
		obs = o;
		goal = g;
		coins = c;
	}
	
	public Level(){
		obs = new Obstacle[0];
		goal = new Goal("Bird");
		coins = new Goal[0];
	}
	
	public  Obstacle[] getObstacles(){
		return obs;
	}
	
	public Goal getGoal(){
		return goal;
	}
	
	public Goal[] getCoins(){
		return coins;
	}
}