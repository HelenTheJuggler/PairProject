
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Level{
	private Obstacle[] obs;
	private Point goalPos;
	private BufferedImage goal;
	
	public Level(Obstacle[] o, Point gp){
		obs = o;
		goalPos = gp;
		try{
			goal = ImageIO.read(new File("Pics\\Goal.png"));
		}catch(Exception e){}
	}
	
	public  Obstacle[] getObstacles(){
		return obs;
	}
	
	public Point getGoalPos(){
		return goalPos;
	}
	
	public BufferedImage getGoalImage(){
		return goal;
	}
}
