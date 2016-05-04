
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
	private double oldReleaseAngle;
	private int magnitude;
	private Timer runTime;
	private Timer animationTime;
	
	private int armLength;
	private Point cupLoc;
	
	private Point fulcrum;
	private int groundHeight;
	private int catapultXLoc;
	private double catRatio;
	private double armRatio;
	private double gearRatio;
	
	private Color sky;
	private Game game;
	
	private boolean launching;
	private boolean adjustGear;
	private boolean adjustArm;
	
	public Catapult(Game g){
		game = g;
		groundHeight = 20;
		catapultXLoc = 50;
		sky = new Color(145, 214, 239);
		
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(700,400));
		cupLoc = new Point(0,0);
		
		try {
			catapultBody = ImageIO.read(new File("Pics\\Catapult.png"));
			emptyArm = ImageIO.read(new File("Pics\\EmptyArm.png"));
			catapultArm = emptyArm;
			gear = ImageIO.read(new File("Pics\\Gear2.png"));
			catapultBottom = ImageIO.read(new File("Pics\\CatapultBottom.png"));
		} catch (IOException e) {}
		releaseAngle = Math.PI/4;
		direction = releaseAngle;
		oldReleaseAngle = releaseAngle;
		
		adjustGear = false;
		adjustArm = false;
		
		runTime = new Timer(10, this);
		setBackground(sky);
		
		animationTime = new Timer(5, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direction += Math.PI/40;
				setCupLoc();
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
		
		armLength = (int) (catapultArm.getWidth()*armRatio*0.65);
		
		repaint();
	}
	
	public void startLaunch(BufferedImage catInCatapult){
		catapultArm = catInCatapult;
		releaseAngle = Math.PI/2;
		direction = releaseAngle;
		launching= true;
		resize();
		setCupLoc();
		repaint();
	}
	
	public double[] getReleaseVelocity(){
		//switch cos and sin since velocity perpendicular to arm
		double[] releaseV = {Math.sin(releaseAngle)*magnitude, Math.cos(releaseAngle)*magnitude};
		return releaseV;
	}
	
	public Point getReleasePosCenter(){
		return cupLoc;
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

		if(adjustArm){
			if(newDirection<Math.PI*0.03 || newDirection>Math.PI)
				direction = Math.PI*0.03;
			else if(newDirection>releaseAngle)
				direction = releaseAngle;
			else
				direction = newDirection;
		}else if(adjustGear){
			if(newDirection>=0 && newDirection<=Math.PI){
				releaseAngle = newDirection;
				direction = releaseAngle;
			}
		}
		
		setCupLoc();
	}
	
	private void setCupLoc(){
		cupLoc.setLocation(fulcrum.getX() - Math.cos(direction)*armLength, fulcrum.getY() - Math.sin(direction)*armLength);
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
		BufferedImage scaleGear = scaleImage(this.gear, gearRatio);
		tx = AffineTransform.getTranslateInstance(fulcrum.getX() - scaleGear.getWidth()/2, 
				fulcrum.getY() - scaleGear.getHeight()*.5);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		scaleGear = op.filter(scaleGear, null);
		tx = AffineTransform.getRotateInstance(releaseAngle, fulcrum.getX(), fulcrum.getY());
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		scaleGear = op.filter(scaleGear,  null);
		g2.drawImage(scaleGear, 0, 0, new Color(0,0,0,0), null);
		
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
	public void mousePressed(MouseEvent squeak) {
		if(launching){
			Point mouse = new Point(squeak.getX(), squeak.getY());
			mouse.translate(0, 20);
			
			BufferedImage scaleGear = scaleImage(this.gear, gearRatio);
			Ellipse2D gearBounds = new Ellipse2D.Double(fulcrum.getX()-scaleGear.getWidth()/4, 
					fulcrum.getY()-scaleGear.getWidth()/4, scaleGear.getWidth()/2, scaleGear.getWidth()/2);
			
			int width = (int) (150*catRatio);
			Ellipse2D armBounds = new Ellipse2D.Double(cupLoc.getX() - width/2, cupLoc.getY()-width/2,
					width, width);
			
			if(gearBounds.contains(mouse.getX(), mouse.getY())){
				adjustGear = true;
				oldReleaseAngle = releaseAngle;
			}else if(armBounds.contains(mouse.getX(), mouse.getY())){
				adjustArm=true;
			}
			runTime.start();
		}
	}
	
	public void mouseReleased(MouseEvent arg0) {
		if(launching){
			runTime.stop();
			if(adjustArm){
				calculateMagnitude();
				animationTime.start();
				launching = false;
				adjustArm = false;
			}if(adjustGear){
				adjustGear = false;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		calculateAngle();
		repaint();
	}
}
