import java.awt.Point;
import java.awt.image.BufferedImage;

public class MovingGoal extends Goal{
	private BufferedImage[] images;
	private Point pos;
	int deltaX, deltaY;
	int n;
	int stop, start;
	
	public MovingGoal(Point pos, String type, boolean horizontal, int speed, int stop, int start) {
		super(pos, type);
		if(horizontal){
			deltaX = speed;
			deltaY = 0;
		}else{
			deltaX = 0;
			deltaY = speed;
		}
		n = 0;
		this.stop = stop;
		this.start = start;
	}
	
	public int getX(){
		return (int)pos.getX();
	}
	
	public int getY(){
		return (int)pos.getY();
	}

}
