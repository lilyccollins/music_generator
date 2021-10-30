package graphics;

import composer.Sheet;

/**
 * Represents the screen shown displaying the notes of the new music
 *
 */
public class OutputScreen extends Screen{

	private DrawingSurface surface;
	private String songName;

	/**
	 * Initializes the size of the window and a surface
	 * @param surface
	 */
	public OutputScreen(DrawingSurface surface)
	{
		super(800, 600);
		this.surface = surface;
	}
	
	/**
	 * Draws on the initialized surface
	 */
	public void draw() {
		 
		surface.pushStyle();
		surface.background(255,255,255);
		
		surface.fill(0);
		surface.textSize(18);
		
		Sheet s = surface.getNewSheet();
		String str = s.getABCNotation();
		
		String str1 = str.substring(0, str.indexOf("Music") + 5);
		surface.text(str1, 10, 100);
		String str2 = str.substring(str.indexOf("C:"), str.indexOf("Jing") + 4);			
		surface.text(str2, 10, 130);
		String str3 = str.substring(str.indexOf("M:"), str.indexOf("L:"));
		surface.text(str3, 10, 160);
		String str4 = str.substring(str.indexOf("L:"), str.indexOf("Q:"));
		surface.text(str4, 10, 190);
		String str5 = str.substring(str.indexOf("Q:"), str.indexOf("K:"));
		surface.text(str5, 10, 220);
		String str6 = str.substring(str.indexOf("K:"), str.indexOf("V:"));
		surface.text(str6, 10, 250);
		String str7 = str.substring(str.indexOf("V:"), str.indexOf("V:") + 3);
		surface.text(str7, 10, 280);
		String music1 = str.substring(str.indexOf("V:") + 3, str.indexOf("V:") + 90);
		surface.text(music1, 10, 320);
		String music2 = str.substring(str.indexOf("V:") + 90, str.indexOf("V:") + 180);
		surface.text(music2, 10, 380);
		String music3 = str.substring(str.indexOf("V:") + 180, str.indexOf("V:") + 270);
		surface.text(music3, 10, 410);
		String music4 = str.substring(str.indexOf("V:") + 270);
		surface.text(music4, 10, 440);
	
		//surface.text(str, 10, 180);		
		
		surface.popStyle();

	}
	

}
