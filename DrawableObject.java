import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * 
 * @author daniel zateikin
 * this class represents a drawable object which has an image and coordinates and a paint
 * method. every game object is a drawable object
 */
public class DrawableObject {
	protected int x;
	protected int y;
	protected ImageIcon objectImg;
	protected int height, width;
	protected boolean visible;
	
	public DrawableObject(int x, int y, String imgPath){
		this.objectImg = new ImageIcon(imgPath);
		this.width = objectImg.getIconWidth();
		this.height = objectImg.getIconHeight();
		this.x = x;
		this.y = y;
		this.visible = true;
	}
	
	public boolean isVisible(){
		return visible;
	}
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void paintObject(Graphics g, GameUI gui){
		if (this.visible){
			objectImg.paintIcon(gui, g, x, y);
		}
	}
}
