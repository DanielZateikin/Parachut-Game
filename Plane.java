
/**
 * 
 * @author daniel zateikin
 * plane object extends Game Object.
 */
public class Plane extends GameObj {
	
	public Plane(int x, int y, String imgPath, int velX, int velY){
		super(x, y, imgPath, velX, velY);		
	}
	
	public void move(){
		this.x = this.x + velX;
	}
	

}
