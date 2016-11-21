
/**
 * 
 * @author daniel zateikin
 * a class for the game Objects. it sets a velocity property for the objects, and inherits from
 * a drawable object
 */
public abstract class GameObj extends DrawableObject {

	protected int velX, velY; 
	
	public GameObj(int x, int y, String imagePath, int velX, int velY){
		super(x, y, imagePath);
		this.velX = velX;
		this.velY = velY;
	}
	
	public void setVelX(int velX){
		this.velX = velX;
	}
	public void setVelY(int velY){
		this.velY = velY;
	}
	public int getVelX(){
		return velX;
	}
	public int getVelY(){
		return velY;
	}
	
	/**
	 * this function checks whether or not two square objects are colliding with each
	 * other and returns true if they are and false otherwise.
	 */
	
	public boolean checkCollision(GameObj other){
		if (x > other.getX() + other.getWidth()){
			return false;
		}
		if (x + width < other.x){
			return false;
		}
		if (y > other.getY() + other.getHeight()){
			return false;
		}
		if (y + height < other.getY()){
			return false;
		}
		return true;
	}
	
	public int getOverlap(GameObj other){
		int yOverlap;
		int xOverlap;
		if (this.y + this.height > other.getY() && this.y + this.height < other.getY() + other.getHeight()){
			yOverlap = this.y + this.height - other.getY();
		}
		else if (this.y > other.getY() && this.y < other.getY() + other.getHeight()){
			yOverlap = other.getY() + other.getHeight() - this.y;
		}
		else{
			yOverlap = 0;
		}
		
		if (this.x + this.width >  other.getX() && this.x + this.width < other.getX() + other.getWidth()){
			xOverlap = this.x + this.width - other.getX();
		}
		else if (this.x > other.getX() && this.x < other.getX() + other.getWidth()){
			xOverlap = other.getX() + other.getWidth() - this.x;
		}
		else{
			xOverlap = 0;
		}
			
		return xOverlap*yOverlap;
	}
	
	
	public abstract void move();
	
	
}
