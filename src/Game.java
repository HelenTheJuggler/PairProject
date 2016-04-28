
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener{
	private Window win;
	private JViewport viewport;
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
	
	public Game(Window w){
		win = w;
		
		setMinimumSize(new Dimension(200,100));
		setSize(new Dimension(1700,800));
		
		groundHeight = 20;
		sky = new Color(145, 214, 239);
		setBackground(sky);
		
		time = new Timer(10, this);
		cata = new Catapult(this);
		kitty = new Cat(false);
		obs = new Obstacle[0];
		launching = true;
		
		setLayout(null);
		add(cata);
		Insets insets = getInsets();
		cata.setBounds(insets.left+10, insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
		repaint();
		
		setUpViewport();
	}
	
	private void setUpViewport(){
		viewport = new JViewport();
		viewport.setView(this);
		viewport.setExtentSize(new Dimension(700,400));
		viewport.setViewPosition(new Point(0, getHeight()-400));
	}
	
	public void paint(Graphics g){
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
		
		Insets insets = getInsets();
		cata.setBounds(insets.left+10, insets.top + getHeight() + cata.getGroundHeight() - 220, 350, 200);
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
		viewport.setViewPosition(new Point(0, getHeight()-400));
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
	
	public void actionPerformed(ActionEvent e){
		kitty.runProjectionMotion();
		for(int x = 0; x < obs.length; x ++){
			if(kitty.collide(obs[x].getRectangle())){
				kitty.hitObstacle();
			}
		}
		if(kitty.collide(new Rectangle(0, getHeight() - groundHeight, getWidth()+1, groundHeight+1))){
			kitty.hitGround();
			time.stop();
			waitTime = new Timer(2000, new ActionListener(){
				public void actionPerformed(ActionEvent e){
					waitTime.stop();
					win.gameComplete();
				}
			});
			waitTime.start();
		}
		adjustViewport();
		repaint();
	}
	private void adjustViewport(){
		Point catPos = kitty.getPosition();
		Point viewPos = viewport.getViewPosition();
		Dimension viewSize = viewport.getViewSize();
		
		double edgeDist = catPos.getX()-viewPos.getX()-viewSize.getWidth();
		System.out.println(edgeDist);
		if(edgeDist<150){
			Point pos = new Point((int)(150-edgeDist + viewPos.getX()), (int)(viewPos.getY()));
			viewport.setViewPosition(pos);
		}
		edgeDist = -catPos.getY()+viewPos.getY()+viewSize.getHeight();
		if(edgeDist<150){
			Point pos = new Point((int)(viewPos.getX()), (int)(-150+edgeDist + viewPos.getY()));
			viewport.setViewPosition(pos);
		}
			
	}
}
