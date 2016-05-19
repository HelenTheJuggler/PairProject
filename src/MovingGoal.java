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
		isReverse = false;
		initImages();
	}
	
	private void initImages(){
		String filePath = "Pics\\FlyingBirds\\";
		
		images = new BufferedImage[6];
		reverse = new BufferedImage[6];
		BufferedImage img;
		try{
			for(int i=1; i<=4; i++){
				images[i-1] = ImageIO.read(new File(filePath + "Flying" + i + ".png"));
				reverse[i-1] = ImageIO.read(new File(filePath + "Reverse" + i + ".png"));
			}
			images[4] = images[2];
			images[5] = images[1];
			reverse[4] = reverse[2];
			reverse[5] = reverse[1];
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
				isReverse = !isReverse;
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
