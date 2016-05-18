
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	private Window win;
	private Settings settings;
	private Timer time;
	private Timer waitTime;
	private Timer goalTime;
	private int score;
	private int tempScore;
	
	private Cat kitty;
	private Level lev;
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
		lev = new Level();
		
		launching = true;
		goalTime = new Timer(10, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((MovingGoal) lev.getGoal()).move();
				repaint();
			}
		});
		
		setLayout(null);
		add(cata);
		Insets insets = getInsets();
		cata.setBounds(insets.left+10, insets.top + getHeight() + cata.getGroundHeight() - 220, 400, 200);
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
		cata.setBounds(insets.left+10 + deltaX, deltaY + insets.top + getHeight() + cata.getGroundHeight() - 220, 400, 200);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//draw sky
		int skyX = (deltaX-700)%2000 + 700;
		g2.translate(skyX, deltaY);
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		g2.translate(-skyX, -deltaY);
		skyX = (deltaX-700)%2000 + 700 - 2000;
		g2.translate(skyX, deltaY);
		g2.drawImage(win.getSettings().getTheme().getSkyImage(), 0, -600, null, null);
		g2.translate(-skyX, -deltaY);
		
		//draw ground
		g2.translate(0, deltaY);
		g2.setColor(settings.getTheme().getGroundColor());
		g2.fill(new Rectangle(0, getHeight() - groundHeight, getWidth()+1, getHeight()+1));
		g2.translate(deltaX, 0);
		
		//draw obstacles
		Obstacle[] obs = lev.getObstacles();
		Obstacle ob;
		for(int x = 0; x < obs.length; x ++){
			ob = obs[x];
			g2.drawImage(ob.getImage(), ob.getX(), ob.getY(), null);
		}
		
		//draw goal
		Goal goal = lev.getGoal();
		if(!goal.isAccomplished()){
			g2.drawImage(goal.getGoalImage(), goal.getX(), goal.getY(), null);
		}
		
		//draw "coins"
		for(int i=0; i<lev.getCoins().length; i++){
			Goal coin = lev.getCoins()[i];
			if(!coin.isAccomplished()){
				g2.drawImage(coin.getGoalImage(), coin.getX(), coin.getY(), null);
			}
		}
		
		g2.translate(-deltaX, -deltaY);
		super.paint(g);
		g2.translate(deltaX, deltaY);
		
		//draw cat
		if(!launching){
			g2.drawImage(kitty.getImage(), 
					(int)kitty.getPosition().getX(), 
					(int)kitty.getPosition().getY(), null);
		}
		
		if(launching){
			cata.requestFocus();
		}
		
		//draw score
		g2.translate(-deltaX, -deltaY);
		g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g2.setColor(settings.getTheme().getFontColor());
		g2.drawString("Score: " + (score+tempScore), 10, 30);	
	}
	
	public void launchComplete(){
		launching = false;
		kitty.launch(cata.getReleaseVelocity(), this.getReleasePosition());
		time.start();
	}
	
	public void startLaunch(Level l){
		Insets insets = getInsets();
		cata.setBounds(insets.left+1,insets.top + getHeight() + cata.getGroundHeight() - 220, 400, 200);
		kitty.reset();
		lev = l;
		tempScore = 0;
		
		ArrayList<Obstacle> overlap = new ArrayList<Obstacle>();
		Obstacle[] obs = l.getObstacles();
		for(int i=0; i<obs.length; i++){
			if(obs[i].getRectangle().intersects(cata.getBounds())){
				overlap.add(obs[i]);
			}
		}
		ArrayList<Goal> overlap2 = new ArrayList<Goal>();
		Goal[] coins = l.getCoins();
		for(int i=0; i<coins.length; i++){
			if(coins[i].getGoalBounds().intersects(cata.getBounds())){
				overlap2.add(coins[i]);
			}
		}
		cata.startLaunch(kitty.getImage(), overlap.toArray(new Obstacle[overlap.size()]), overlap2.toArray(new Goal[overlap2.size()]));
		if(lev.isMoving()){
			goalTime.start();
		}
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
	public int getScore(){
		return score;
	}
	public boolean isComplete(){
		return lev.getGoal().isAccomplished();
	}
	public void actionPerformed(ActionEvent e){
		kitty.runProjectionMotion();
		
		Obstacle[] obs = lev.getObstacles();
		for(int x = 0; x < obs.length; x ++){
			if(kitty.collide(obs[x].getRectangle())){
				boolean top = kitty.getPosition().getX() + kitty.getWidth()*0.75>=obs[x].getX();
				kitty.hitObstacle(top);
				if(top){
					runComplete();
				}
			}
		}
		
		Goal[] coins = lev.getCoins();
		for(int x=0; x<coins.length; x++){
			if(kitty.collide(coins[x].getGoalBounds()) && !coins[x].isAccomplished()){
				coins[x].accomplished();
				tempScore+=2;
			}
		}
			
		if(kitty.collide(lev.getGoal().getGoalBounds()) && !lev.getGoal().isAccomplished()){
			tempScore+=10;
			lev.getGoal().accomplished();
			goalTime.stop();
			win.nextLevel();
		}
		
		if(kitty.getPosition().getY()>getHeight()-kitty.getHeight()-groundHeight){
			kitty.hitGround();
			runComplete();
		}
		repaint();
	}
	
	private void runComplete(){
		time.stop();
		waitTime = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(isComplete()){
					score += tempScore;
				}
				waitTime.stop();
				win.gameComplete();
				launching = true;
			}
		});
		waitTime.start();
	}
}
