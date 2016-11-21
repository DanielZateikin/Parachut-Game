

/**
 * 
 * @author daniel zateikin
 * the Parachut Object can be alive or not.
 * the move and paint methods are being called only when the object is alive.
 */

public class Parachut extends GameObj {

	public Parachut(int x, int y, String imgPath, int velX, int velY) {
		super(x, y, imgPath, velX, velY);
		this.visible = false;
	}
	
	
	public void move(){
		if (visible){
			this.y = y + velY;
		}
	}
	
	
	public void reset(int dropPointX, int dropPointY){
		this.visible = false;
		this.x = dropPointX;
		this.y = dropPointY;
		
	}
	
}
