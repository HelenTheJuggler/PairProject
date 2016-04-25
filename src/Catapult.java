
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
	
	private Color sky;
	private Game game;
	
	public Catapult(Game g){
		game = g;
		groundHeight = 20;
		catapultXLoc = 50;
		sky = new Color(145, 214, 239);
		
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(700,400));
		
		try {
			catapultBody = ImageIO.read(new File("Pics\\Catapult.png"));
			catapultArm = ImageIO.read(new File("Pics\\EmptyArm.png"));
		} catch (IOException e) {}
		releaseAngle = Math.PI/3;
		direction = releaseAngle;
		
		runTime = new Timer(10, this);
		setBackground(sky);
		
		animationTime = new Timer(5, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direction += Math.PI/48;
				if(direction>= releaseAngle){
					direction = releaseAngle;
					animationTime.stop();
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
		releaseAngle = Math.PI/3;
		direction = releaseAngle;
		repaint();
	}
	
	public double[] getReleaseVelocity(){
		double[] releaseV = {Math.cos(releaseAngle)*magnitude, Math.sin(releaseAngle)*magnitude};
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
		
		direction = -(Math.PI-Math.atan2(y,x)) + 2*Math.PI;
	}
	
	private void calculateMagnitude(){
		//dies if arm pulled back too far(goes super huge)
		
		magnitude = (int) (50*Math.abs(releaseAngle-direction));
		//System.out.println(magnitude);
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
	}
	
	public static BufferedImage scaleImage(BufferedImage image, double ratio){
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(ratio, ratio);
		AffineTransformOp op = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image,  null);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//draw ground
		g.setColor(new Color(0, 102, 0));
		g.fill3DRect(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1, false);
		
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
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		runTime.start();
	}
	public void mouseReleased(MouseEvent arg0) {
		runTime.stop();
		calculateMagnitude();
		
		//run launch animation
		animationTime.start();
		
		game.launchComplete();
		catapultArm = emptyArm;
	}
	
	public void actionPerformed(ActionEvent e){
		calculateAngle();
		repaint();
	}
}
