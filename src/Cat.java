
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cat {
	BufferedImage catapult;
	BufferedImage flyingCat;
	BufferedImage landingCat;
	BufferedImage catapultCat;
	BufferedImage fallingCat;
	BufferedImage current;
	Point pos;
	double[] vel;
	boolean friction;
	
	public Cat(boolean fr){
		friction = fr;
		BufferedImage cat = null;
		try {
		    cat = ImageIO.read(new File("Catapult\\Pics\\KittenInCup.jpg"));
		    current = cat;
		    catapultCat = cat;
		    cat = ImageIO.read(new File("Catapult\\Pics\\BallKitten.jpg"));
		    flyingCat = cat;
		    cat = ImageIO.read(new File("Catapult\\Pics\\KittenStanding.jpg"));
		    landingCat = cat;
		} catch (IOException e) {
		}
		pos = null;
		vel= null;
		
	}
	public void launch(double[] velocity, Point p){
		vel = velocity;
		pos = p;
		current = flyingCat;
	}
	public void runProjectionMotion(){
		pos.setLocation(pos.getX() + vel[0], pos.getY() + vel[1]);
		//this method is not finished... physics things
	}
	public boolean collide(Rectangle r){
		if(r.contains(pos)){
			return true;
		}
		else{
			return false;
		}
		
	}
	public void hitGround(){
		current = landingCat;
	}
	public void hitObstacle(){
		vel[0] = 0;
		current = fallingCat;
	}
	public BufferedImage getImage(){
		return current;
	}
	public Point getPosition(){
		return pos;
	}
	
}
