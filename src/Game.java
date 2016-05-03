
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	private Window win;
	private Timer time;
	private Timer waitTime;
	private int score;
	
	private Cat kitty;
	private Obstacle[] obs;
	private Catapult cata;
	
	private Point release;
	private int groundHeight;
	private Color sky;
	
	private boolean launching;
	private Point origin;
	
	public Game(Window w){
		win = w;
		
		setMinimumSize(new Dimension(700,400));
		setSize(new Dimension(700,400));
		origin = new Point(0,0);
		
		groundHeight = 20;
		sky = new Color(145, 214, 239);
		setBackground(sky);
		
		time = new Timer(500, this);
		cata = new Catapult(this);
		kitty = new Cat(false);
		obs = new Obstacle[0];
		launching = true;
		
		setLayout(null);
		add(cata);
		Insets insets = getInsets();
		cata.setBounds(insets.left+10, insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
		repaint();
	}
	
	public void paint(Graphics g){
		g.translate((int)origin.getX(), (int)origin.getY());
		
		Insets insets = getInsets();
		cata.setBounds(insets.left+10 + (int)origin.getX(), 
				(int)origin.getY() + insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
		
		super.paint(g);
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
		
		//draw obstacle things
		for(int x = 0; x < obs.length; x ++){
			g2.drawImage(obs[x].getImage(), obs[x].getX(), obs[x].getY(), null);
		}
		
		if(launching){
			cata.requestFocus();
		}
	}
	
	public void launchComplete(){
		launching = false;
		kitty.launch(cata.getReleaseVelocity(), this.getReleasePosition());
		time.start();
	}
	
	public void startLaunch(){
		cata.startLaunch(kitty.getImage());
		origin = new Point(0,0);
	}
	
	public Point getReleasePosition(){
		release = new Point(cata.getX() + cata.getWidth()/3, cata.getY());
		return release;
	}
	
	public Cat getCat(){
		return kitty;
	}
	
	public void setCat(Cat newCat){
		kitty = newCat;
	}
	public Catapult getCatapult(){
		return cata;
	}
	public void actionPerformed(ActionEvent e){
		kitty.runProjectionMotion();
		adjustViewport();
		for(int x = 0; x < obs.length; x ++){
			if(kitty.collide(obs[x].getRectangle())){
				kitty.hitObstacle();
			}
		}
		if(kitty.collide(new Rectangle(0, getHeight() - groundHeight, getWidth()+1, groundHeight+1))){
			kitty.hitGround();
			time.stop();
			waitTime = new Timer(1000, new ActionListener(){
				public void actionPerformed(ActionEvent e){
					waitTime.stop();
					launching = true;
					win.gameComplete();
				}
			});
			waitTime.start();
		}
		repaint();
	}
			
	private void adjustViewport(){
		int x = (int) origin.getX();
		int y = (int) origin.getY();
		
		int xGap = (int) (x + 700 -kitty.getPosition().getX());
		int yGap = (int) (-y + kitty.getPosition().getY());
		System.out.println(xGap + ", " + yGap);
		//System.out.println(x + ", " + y);
		
		if(xGap<150){
			x+= (150-xGap);
		}if(yGap<150){
			y+= (150-yGap);
		}
		//System.out.println(x + ", " + y);
		//origin.setLocation(new Point(x,y));
	}
}
