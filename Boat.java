
/**
 * 
 * @author daniel zateikin
 * the Boat class that can move side to side inside some frame.
 * 
 */
public class Boat extends GameObj{
	
	private int frameWidth;
	/**
	 * this constructor sets the y coordinate of the boat to be what is given minus the 
	 * height of itself.
	 */
	public Boat(int x, int y, String imgPath, int frameWidth, int velX, int velY){
		super(x, y, imgPath, velX, velY);
		this.frameWidth = frameWidth;
		this.x = x - this.height;
	}
	public void move(){
		if (!(x < Math.abs(velX) && velX < 0) && !(x + width  >= frameWidth && velX > 0)){
			x = x + velX;
		}
		
	}
	
	
}
