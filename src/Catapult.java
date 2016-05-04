
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Catapult extends JPanel implements ActionListener, MouseListener{
	private BufferedImage catapultBody;
	private BufferedImage catapultArm;
	private BufferedImage emptyArm;
	private BufferedImage gear;
	private BufferedImage catapultBottom;
	
	private double direction;
	private double releaseAngle; //angle of arm (orthogonal to velocity)
	private int magnitude;
	private Timer runTime;
	private Timer animationTime;
	
	private Point fulcrum;
	private int groundHeight;
	private int catapultXLoc;
	private double catRatio;
	private double armRatio;
	private double gearRatio;
	
	private Color sky;
	private Game game;
	private boolean launching;
	
	public Catapult(Game g){
		game = g;
		groundHeight = 20;
		catapultXLoc = 50;
		sky = new Color(145, 214, 239);
		
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(700,400));
		
		try {
			catapultBody = ImageIO.read(new File("Pics\\Catapult.png"));
			emptyArm = ImageIO.read(new File("Pics\\EmptyArm.png"));
			catapultArm = emptyArm;
			gear = ImageIO.read(new File("Pics\\Gear2.png"));
			catapultBottom = ImageIO.read(new File("Pics\\CatapultBottom.png"));
		} catch (IOException e) {}
		releaseAngle = Math.PI/4;
		direction = releaseAngle;
		
		runTime = new Timer(10, this);
		setBackground(sky);
		
		animationTime = new Timer(5, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direction += Math.PI/48;
				if(direction>= releaseAngle){
					direction = releaseAngle;
					animationTime.stop();
					game.launchComplete();
					catapultArm = emptyArm;
				}
				repaint();
			}
		});
		
		addComponentListener(new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				resize();
			} 
		});
		addMouseListener(this);
		resize();
	}
	
	private void resize(){
		groundHeight = getHeight()/20;
		catapultXLoc = getWidth()/14;
		setResizeRatios();
		fulcrum = new Point((int)(catapultBody.getWidth()*.7*catRatio) + catapultXLoc, 
				getHeight()-groundHeight-(int)(12*catRatio));
		repaint();
	}
	
	public void startLaunch(BufferedImage catInCatapult){
		catapultArm = catInCatapult;
		releaseAngle = Math.PI/2;
		direction = releaseAngle;
		launching= true;
		repaint();
	}
	
	public double[] getReleaseVelocity(){
		//switch cos and sin since velocity perpendicular to arm
		double[] releaseV = {Math.sin(releaseAngle)*magnitude, Math.cos(releaseAngle)*magnitude};
		return releaseV;
	}

	public int getGroundHeight(){
		return groundHeight;
	}
	
	private void calculateAngle(){
		double x;
		double y;
		
		//finds direction of mouse relative to fulcrum of catapult
		Point mouse = new Point(MouseInfo.getPointerInfo().getLocation());
		x = mouse.getX() - fulcrum.getX() - getX();
		y = mouse.getY() - fulcrum.getY() - getY();
		
		double newDirection = -(Math.PI-Math.atan2(y,x)) + 2*Math.PI;
		if(newDirection<Math.PI*0.03 || newDirection>Math.PI)
			direction = Math.PI*0.03;
		else if(newDirection>releaseAngle)
			direction = releaseAngle;
		else
			direction = newDirection;
	}
	
	private void calculateMagnitude(){
		//pull back to far kills magnitude
		magnitude = (int) (50*Math.abs(releaseAngle-direction));
	}
	
	private void setResizeRatios(){
		double w = catapultBody.getWidth();
		double h = catapultBody.getHeight();
		
		double ratio = w/h;
		double scaleRatio = (double)(.7*getHeight())/h;
		
		if(ratio > ((double)getWidth())/getHeight()){
			scaleRatio = (double)(.7*getWidth())/w;
		}
		
		catRatio = scaleRatio;
		armRatio = (catapultBody.getWidth()*catRatio*.9)/catapultArm.getWidth();
		gearRatio = armRatio*0.4;
	}
	
	public static BufferedImage scaleImage(BufferedImage image, double ratio){
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(ratio, ratio);
		AffineTransformOp op = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image,  null);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//draw catapultArm
		BufferedImage arm = scaleImage(catapultArm, armRatio);
		AffineTransform tx = AffineTransform.getTranslateInstance(fulcrum.getX() - arm.getWidth(), 
				fulcrum.getY() - arm.getHeight()*.5);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		arm = op.filter(arm, null);
		tx = AffineTransform.getRotateInstance(direction, fulcrum.getX(), fulcrum.getY());
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		arm = op.filter(arm,  null);
		g2.drawImage(arm, 0, 0, new Color(0,0,0,0), null);
		
		//draw catapult body on top of arm
		g2.drawImage(scaleImage(catapultBody, catRatio), catapultXLoc, 
				getHeight()-groundHeight-(int)(catapultBody.getHeight()*catRatio),new Color(0,0,0,0), null);
		
		//draw gear
		BufferedImage gear = scaleImage(this.gear, gearRatio);
		tx = AffineTransform.getTranslateInstance(fulcrum.getX() - gear.getWidth()/2, 
				fulcrum.getY() - gear.getHeight()*.5);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		gear = op.filter(gear, null);
		tx = AffineTransform.getRotateInstance(releaseAngle, fulcrum.getX(), fulcrum.getY());
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		gear = op.filter(gear,  null);
		g2.drawImage(gear, 0, 0, new Color(0,0,0,0), null);
		
		//draw catapult bottom on top of gear
		g2.drawImage(scaleImage(catapultBottom, catRatio), catapultXLoc, 
				getHeight()-groundHeight-(int)(catapultBody.getHeight()*catRatio),new Color(0,0,0,0), null);
		
		//draw ground
		g.setColor(new Color(0, 102, 0));
		g.fill3DRect(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1, false);
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		
		
		
		if(launching){
			Point mouse = new Point(MouseInfo.getPointerInfo().getLocation());
			
			Polygon arm = new Polygon();
			
			
			
			runTime.start();
		}
	}
	
	private Point rotatePoint(Point p, Point fulcrum, double radians){
		Double x = p.getX()-fulcrum.getX();
		Double y = p.getY()-fulcrum.getY();
		p.setLocation(x*Math.cos(radians)-y*Math.sin(radians), x*Math.sin(radians)+y*Math.cos(radians));
		return p;
	}
	
	public void mouseReleased(MouseEvent arg0) {
		if(launching){
			runTime.stop();
			calculateMagnitude();
			animationTime.start();
			launching = false;
		}
	}
	
	public void actionPerformed(ActionEvent e){
		calculateAngle();
		repaint();
	}
}
