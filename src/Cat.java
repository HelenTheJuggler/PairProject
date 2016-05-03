
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
	double gravity = .75;
	
	public Cat(boolean fr){
		friction = fr;
		initImages();
		scaleImages();
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
		} catch (IOException e) {}
	}
	
	private void scaleImages(){
		double catRatio = 75.0/flyingCat.getWidth();
		flyingCat = Catapult.scaleImage(flyingCat, catRatio);
		catRatio = 80.0/landingCat.getWidth(); 
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
		if(r.intersects(getBounds())){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Rectangle getBounds(){
		return (new Rectangle((int)pos.getX(), (int)pos.getY(), current.getHeight(), current.getWidth()));
	}
	
	public void hitGround(){
		pos.setLocation(pos.getX(), pos.getY() + current.getHeight() - landingCat.getHeight() + 5);
		current = landingCat;
	}
	
	public void hitObstacle(){
		vel[0] = 0;
		current = fallingCat;
	}
	public void newCat(){
		current = catapultCat;
	}
	public BufferedImage getImage(){
		return current;
	}
	
	public Point getPosition(){
		return pos;
	}
	public void setFriction(boolean change){
		friction = change;
	}
}
