import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author Daniel Zateikin
 * this class is responsible for drawing the game objects, it knows about drawable objects
 * and calls their paint method in its paint function.
 */
public class GameUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<DrawableObject> drawObjects;
	private Image background;
	
	public int frameWidth;
	public int frameHeight;
	
	
	public GameUI(){
		//setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		drawObjects = new ArrayList<DrawableObject>();
	}
	
	public void setBackground(Image background){
		this.background = background;
	}
	
	public void addDrawable(DrawableObject drawObject){
		this.drawObjects.add(drawObject);
	}
	/**
	 * go over all drawable objects and paint them with their mehod.
	 * 
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		
		for (DrawableObject drawObject: drawObjects){
			drawObject.paintObject(g, this);
		}
	}
	
}
