=====================
Vonage Parachut Game:
By: Daniel Zateikin
=====================

usage:
	have all the files in the same directory, and at that same directory hold the 
	images under 'images' directory.
	compile and run GameLogic.java (main class)

Design:

	Game Objects:
			
		1. Drawable:
		there is a base Drawable Class that represents the objects
		as drawable to be used by the GUI class.
		
		2. GameObject (inherits from Drawable):
		this class inherits from the Drawable class and represents a generic
		Game object that has a velocity and implements a collision method.
		
		3. Boat, Plane, Parachut (inherit from GameObject).
		specific objects for the game.
			
	Gui Class:
		Holds Drawable Objects.
		
	Main Logic Game Class:
		Implements the rules of the game and the game status.
		Holds a GUI class, Holds all Game Objects.
		Gets input from the keyboard and passes requests for actions
		to the Game Objects and the GUI.
	
files:
	1. DrawableObject.java
	2. GameObj.java
	3. Boat.java
	4. Plane.java
	5. Parachut.java
	6. GameUI.java
	7. GameLogic.java  - main class
	
images:
	1. background - images/seaS.jpg
	2. plane      - images/planeS.png
	3. Boat       - images/bboatS.gif
	4. parachute  - images/parachuteS.png
	


