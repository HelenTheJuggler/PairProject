
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//
public class Cat {
	BufferedImage flyingCat;
	BufferedImage landingCat;
	BufferedImage catapultCat;
	BufferedImage fallingCat;
	BufferedImage current;
	Point pos;
	double[] vel;
	boolean friction;
	double gravity = 0.5;
	
	public Cat(boolean fr){
		friction = fr;
		initImages();
		pos = new Point(0,0);
		vel= new double[2];
	}
	
	private void initImages(){
		//can specialize in subclasses of cat
		BufferedImage cat = null;
		try {
		    cat = ImageIO.read(new File("Pics\\KittenInCup.png"));
		    current = cat;
		    catapultCat = cat;
		    cat = ImageIO.read(new File("Pics\\BallKitten.png"));
		    flyingCat = cat;
		    cat = ImageIO.read(new File("Pics\\StandingKitten.png"));
		    landingCat = cat;
		} catch (IOException e) {
		}
		
		double catRatio = 75.0/flyingCat.getWidth();
		flyingCat = Catapult.scaleImage(flyingCat, catRatio);
		catRatio = 150.0/landingCat.getWidth(); 
		landingCat = Catapult.scaleImage(landingCat, catRatio);
	}
	
	public void launch(double[] velocity, Point p){
		vel = velocity;
		pos = p;
		current = flyingCat;
	}
	
	public void runProjectionMotion(){
		//can specialize in subclasses of cat
		pos.setLocation(pos.getX() + vel[0]/5, pos.getY() - vel[1]/5);
		vel[1]-=gravity;
		if(friction){
			vel[0]-=1;
			if(vel[1]<0)
				vel[1]+=1;
			else
				vel[1]-=1;
		}
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
