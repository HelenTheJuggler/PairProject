
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	private Window win;
	private Settings settings;
	private Timer time;
	private Timer waitTime;
	private int score;
	
	private Cat kitty;
	private Obstacle[] obs;
	private Catapult cata;
	
	private Point release;
	private int groundHeight;
	
	private boolean launching;
	
	public Game(Window w){
		win = w;
		settings = win.getSettings();
		
		setMinimumSize(new Dimension(700,400));
		setSize(new Dimension(700,400));
		
		groundHeight = 20;
		setBackground(new Color(0,0,0,0));
		
		time = new Timer(10, this);
		cata = new Catapult(this);
		kitty = new Cat();
		obs = new Obstacle[1];
		obs[0] = new Obstacle(400, getHeight() - groundHeight - 100, null);
		
		launching = true;
		
		waitTime = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				waitTime.stop();
				launching = true;
				win.gameComplete();
			}
		});
		
		setLayout(null);
		add(cata);
		Insets insets = getInsets();
		cata.setBounds(insets.left+10, insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
		repaint();
	}
	
	public void paint(Graphics g){
		int deltaX = 0;
		int deltaY = 0;
		
		if(!launching){
			deltaX = -(int) Math.max((kitty.getPosition().getX()- getWidth()*0.5),0);
			deltaY = (int) Math.max(0,-(-getHeight()*0.25 + kitty.getPosition().getY()));
		}
		
		Insets insets = getInsets();
		cata.setBounds(insets.left+10 + deltaX, deltaY + insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
		
		//draw sky
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(deltaX, deltaY);
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		g2.translate(-deltaX, -deltaY);
		
		super.paint(g);
		
		//draw ground
		g2.translate(0, deltaY);
		g.setColor(settings.getTheme().getGroundColor());
		g.fill3DRect(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1, false);
		g2.translate(deltaX, 0);
		
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
	}
	
	public Point getReleasePosition(){
		release = cata.getReleasePosCenter();
		release.translate(cata.getX(), cata.getY());
		release.translate(0, -kitty.getHeight()/3);
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
	public Theme getTheme(){
		return settings.getTheme();
	}
	public void actionPerformed(ActionEvent e){
		kitty.runProjectionMotion();
		for(int x = 0; x < obs.length; x ++){
			if(kitty.collide(obs[x].getRectangle())){
				boolean bool = kitty.getPosition().getY() + kitty.getHeight() > obs[x].getY();
				if(bool){
					time.stop();
					waitTime.start();
				}
				kitty.hitObstacle(bool);
			}
		}
		if(kitty.getPosition().getY()>getHeight()-kitty.getHeight()-groundHeight){
			kitty.hitGround();
			time.stop();
			waitTime.start();
		}
		repaint();
	}
}
