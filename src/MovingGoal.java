import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MovingGoal extends Goal{
	private BufferedImage[] images;
	private BufferedImage[] reverse;
	private Point pos;
	int speed;
	int stop, start;
	boolean horizontal;
	int currentImg;
	int n;
	boolean isReverse;
	
	public MovingGoal(Point pos, String type, boolean horizontal, int speed, int stop, int start) {
		super(pos, type);
		this.pos = pos;
		this.horizontal = horizontal;
		this.speed = speed;
		this.stop = stop;
		this.start = start;
		currentImg = 0;
		isReverse = true;
		initImages();
	}
	
	private void initImages(){
		images = new BufferedImage[5];
		reverse = new BufferedImage[5];
		BufferedImage img;
		try{
			img = ImageIO.read(new File("Pics\\Bird.png"));
			for(int i=0; i<5; i++){
				images[i] = img;
				reverse[i] = img;
			}
		}catch(Exception e){}
		
		for(int i=0; i<images.length; i++){
			images[i] = Catapult.scaleImage(images[i], 75.0/images[i].getWidth());
			reverse[i] =  Catapult.scaleImage(reverse[i], 75.0/reverse[i].getWidth());
		}
	}
	
	public void move(){
		if(horizontal){
			if(pos.getX() + speed > stop || pos.getX() + speed<start){
				speed *= -1;
				isReverse = false;
			}
			pos.setLocation(pos.getX() + speed, pos.getY());
		}else{
			if(pos.getY() + speed > stop || pos.getY() + speed<start)
				speed *= -1;
			pos.setLocation(pos.getX(), pos.getY() + speed);
		}
		
		if(n%3==0){
			currentImg = (currentImg + 1)%images.length;
		}
		n++;
	}
	
	public int getX(){
		return (int)pos.getX();
	}
	
	public int getY(){
		return (int)pos.getY();
	}
	
	public BufferedImage getGoalImage(){
		if(isReverse){
			return reverse[currentImg];
		}else{
			return images[currentImg];
		}
	}

}
