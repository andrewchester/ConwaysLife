import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/*
 * The individual cell object, draws itself, has a rectangle to detect mouse clicks, some variables for whether it's alive and it's going to be alive next round, thats about it
 * Also, You have to have 2 variables for the cells, if it's alive right now and if it's going to be alive next round, otherwise you'll be switching its state when you're updating other cells, which change how they'll update
 */
public class Cell {
	private boolean alive;
	private boolean alive_next;
	private Rectangle bounds;
	private int xPos, yPos;
	Cell(int x, int y, int width, int height){
		bounds = new Rectangle(x,y,width,height);
		alive = false;
	}
	public void draw(Graphics g){
		if(alive){
			g.setColor(Color.black);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}else{
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
	public void toggleLife(){
		if(!alive)
			alive = true;
		else
			alive = false;
	}
	public Rectangle getBounds(){return bounds;}
	public void setBounds(Rectangle bounds){this.bounds = bounds;}
	public boolean getLife(){return alive;}
	public void setLife(boolean alive){this.alive = alive;}
	public void setPos(int xPos, int yPos) {this.xPos = xPos; this.yPos = yPos;}
	public int getXPos() {return xPos;}
	public int getYPos() {return yPos;}
	public boolean getAliveNext(){return alive_next;}
	public void setAliveNext(boolean alive_next){this.alive_next = alive_next;}
}
