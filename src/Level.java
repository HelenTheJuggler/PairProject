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
	private String text;
	
	public Level(Obstacle[] o, Goal g, Goal[] c, String text){
		obs = o;
		goal = g;
		coins = c;
		this.text = text;
	}
	
	public Level(){
		obs = new Obstacle[0];
		goal = new Goal("Bird");
		coins = new Goal[0];
		text = "";
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
	
	public String getText(){
		return text;
	}
}