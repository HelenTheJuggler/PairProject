
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//
public class Cat {
	private BufferedImage flyingCat;
	private BufferedImage landingCat;
	private BufferedImage catapultCat;
	private BufferedImage current;
	private Point pos;
	private double[] vel;
	private boolean friction;
	private double gravity = .75;
	private String name = "Kitten";
	private String color = "Orange";
	
	public Cat(){
		friction = false;
		initImages();
		pos = new Point(0,0);
		vel= new double[2];
	}
	
	private void initImages(){
		//can specialize in subclasses of cat
		BufferedImage cat = null;
		try {
		    cat = ImageIO.read(new File("Pics\\Cats\\"+ color + "Cats\\"+ name +"InCup.png"));
		    catapultCat = cat;
		    cat = ImageIO.read(new File("Pics\\Cats\\"+ color + "Cats\\Ball"+ name + ".png"));
		    flyingCat = cat;
		    cat = ImageIO.read(new File("Pics\\Cats\\"+ color + "Cats\\Standing"+ name +".png"));
		    landingCat = cat;
		} catch (IOException e) {}
		
		double catRatio = 75.0/flyingCat.getWidth();
		flyingCat = Catapult.scaleImage(flyingCat, catRatio);
		catRatio = 80.0/landingCat.getWidth(); 
		landingCat = Catapult.scaleImage(landingCat, catRatio);
		
		current = catapultCat;
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
			vel[0]-=0.05*vel[0];
			vel[1]-=0.05*vel[1];

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
		pos.setLocation(pos.getX() + current.getWidth()-landingCat.getWidth(), 
				pos.getY() + current.getHeight() - landingCat.getHeight() + 10);
		current = landingCat;
	}
	
	public void hitObstacle(boolean hitTop){
		vel[0] = 0;
		if(hitTop){
			System.out.println("!!!!!!");
			vel[1] = 0;
			//hitGround();
		}
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
	
	public int getHeight(){
		return current.getHeight();
	}
	
	public int getWidth(){
		return current.getWidth();
	}
	
	public void setFriction(boolean change){
		friction = change;
	}
	
	public void setColor(String color){
		this.color = color;
		initImages();
	}
	
	public void setType(String name){
		this.name = name;
		initImages();
	}
}
