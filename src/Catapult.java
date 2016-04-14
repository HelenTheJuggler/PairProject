
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Catapult extends JPanel implements ActionListener, MouseListener{
	private Image catapultBody;
	private Image catapultArm;
	
	private double direction;
	private double releaseAngle; //angle of arm (orthogonal to velocity)
	private int magnitude;
	private Timer runTime;
	private Timer animationTime;
	
	private Point fulcrum;
	private Point catapultLoc;
	private int groundHeight = 20;
	private Color sky = Color.cyan;
	
	public Catapult(){
		setPreferredSize(new Dimension(200,100));
		setSize(new Dimension(200,100));
		
		ImageIcon icon = new ImageIcon("PairProject\\Pics\\Catapult.jpg");
		catapultBody = icon.getImage();
		
		/*try {
		    catapultBody = ImageIO.read(new File("PairProject\\Pics\\Catapult.jpg"));
		} catch (IOException e) {
			System.out.println("EXCEPTION");
		}*/
		
		fulcrum = new Point(0, getHeight()-groundHeight);
		
		System.out.println(getHeight());
		System.out.println(groundHeight);
		System.out.println(catapultBody);
		System.out.println(catapultBody.getHeight(null));
		
		catapultLoc = new Point(0, getHeight()-groundHeight-catapultBody.getHeight(null));
		
		runTime = new Timer(10, this);
		setBackground(sky);
		
		animationTime = new Timer(25, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				direction -= Math.PI/12;
				if(direction>=releaseAngle){
					direction = releaseAngle;
					animationTime.stop();
				}
				repaint();
			}
		});
	}
	
	public void startLaunch(Image catInCatapult){
		catapultArm = catInCatapult;
		releaseAngle = Math.PI/4;
	}
	
	public double[] getReleaseVelocity(){
		double[] releaseV = {Math.cos(releaseAngle)*magnitude, Math.sin(releaseAngle)*magnitude};
		return releaseV;
	}

	private void calculateAngle(){
		double x;
		double y;
		
		//finds direction of mouse relative to fulcrum of catapult
		Point mouse = new Point(MouseInfo.getPointerInfo().getLocation());
		x = mouse.getX() - fulcrum.getX();
		y = mouse.getY() - fulcrum.getY();
		
		direction = Math.atan2(y,x);
	}
	
	private void calculateMagnitude(){
		
	}
	
	public void paint(Graphics g){
		super.repaint();
		Graphics2D g2 = (Graphics2D)g;
		
		//draw ground
		g.setColor(new Color(0, 102, 0));
		g.fill3DRect(0, getHeight() - groundHeight, getWidth(), getHeight(), false);
		
		//draw catapultArm
		AffineTransform notRotated = g2.getTransform();
		g2.rotate(direction);
		g2.drawImage(catapultArm, (int)fulcrum.getX()-catapultArm.getWidth(null), 
				(int)fulcrum.getY()-catapultArm.getHeight(null), sky, null);
		g2.setTransform(notRotated);
		
		//draw catapult body on top of arm
		g2.drawImage(catapultBody, (int)catapultLoc.getX(), 
				(int)catapultLoc.getY(), sky, null);
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
		
		//call launch complete in window
	}
	
	public void actionPerformed(ActionEvent e){
		calculateAngle();
		repaint();
	}
}
