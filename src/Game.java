
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
	private Window win;
	private Timer time;
	private int score;
	
	private Cat kitty;
	private Obstacle[] obs;
	private Catapult cata;
	
	private Point release;
	private int groundHeight;
	private Color sky;
	
	private boolean launching;
	
	public Game(Window w){
		win = w;
		groundHeight = 20;
		sky = new Color(145, 214, 239);
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(700,400));
		setBackground(sky);
		time = new Timer(10, this);
		cata = new Catapult(this);
		kitty = new Cat(false);
		obs = new Obstacle[0];
		launching = true;
	}
	
	public void paint(Graphics g){
		super.repaint();
		Graphics2D g2 = (Graphics2D)g;
		
		//draw ground
		g.setColor(new Color(0, 102, 0));
		g.fill3DRect(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1, false);
		
		//draw cat
		if(!launching){
			g2.drawImage(kitty.getImage(), 
					(int)kitty.getPosition().getX(), 
					(int)kitty.getPosition().getY(), null);
		}
	
		//draw catapult
		g2.drawImage(cata.createImage(cata.getWidth(),cata.getHeight()), 0, 
				getHeight()-groundHeight + cata.getGroundHeight(), null);
		
		//draw obstacle things
		for(int x = 0; x < obs.length; x ++){
			g2.drawImage(obs[x].getImage(), obs[x].getX(), obs[x].getY(), null);
		}
	}
	
	public void launchComplete(){
		launching = false;
		kitty.launch(cata.getReleaseVelocity(), this.getReleasePosition());
		time.start();
	}
	
	public Point getReleasePosition(){
		return release;
	}
	
	public Cat getCat(){
		return kitty;
	}
	
	public void setCat(Cat newCat){
		kitty = newCat;
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
