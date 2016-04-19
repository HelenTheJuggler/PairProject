
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
//
public class Game extends JPanel implements ActionListener{
	Cat kitty;
	Window win;
	Obstacle[] obs;
	Timer time;
	int score;
	Point release;
	Catapult cata;
	int groundHeight;
	Color sky;
	
	public Game(Window w){
		win = w;
		groundHeight = 20;
		sky = new Color(145, 214, 239);
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(700,400));
		setBackground(sky);
		time = new Timer(10, this);
	}
	public void paint(Graphics g){
		super.repaint();
		Graphics2D g2 = (Graphics2D)g;
		
		//draw ground
		g.setColor(new Color(0, 102, 0));
		g.fill3DRect(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1, false);
		
		//ask helen for picture of generic catapult to use in background(with arm)
		g2.drawImage(kitty.getImage(), (int)kitty.getPosition().getX(), (int)kitty.getPosition().getY(), null);
	
		
		
		//draw obstacle things
		for(int x = 0; x < obs.length; x ++){
			g2.drawImage(obs[x].getImage(), obs[x].getX(), obs[x].getY(), null);
		}
		
	}
	public void launchComplete(){
		kitty.launch(cata.getReleaseVelocity(), this.getReleasePosition());
		time.start();
	}
	public Point getReleasePosition(){
		return release;
	}
	public Cat getCat(){
		return kitty;
	}
	public void actionPerformed(ActionEvent e){
		kitty.runProjectionMotion();
		//kitty.collide()
		for(int x = 0; x < obs.length; x ++){
			if(kitty.collide(obs[x].getRectangle())){
				kitty.hitObstacle();
			}
		}
		if(kitty.getPosition().getX() + kitty.getImage().getHeight() <= groundHeight){
			kitty.hitGround();
			time.stop();
			//no end game animation exists
			//win.gameComplete();
			
		}
		repaint();
	}
	
}
