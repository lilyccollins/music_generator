package graphics;
import java.awt.Point;
import java.awt.Rectangle;

import composer.Sheet;

/**
 * Represents the screen shown when the user selects Beethoven
 *
 */
public class BeethovenScreen extends Screen {

	private DrawingSurface surface;
	
	private Rectangle song1;
	private Rectangle song2; // buttons for each song
	private Rectangle song3;
	private Rectangle composeButton;
	
	private String furElise = "data/Beethoven/fur_elise_C1V";
	private String furEliseAudio = "data/Beethoven/FurEliseAudio.mp3";	

	private String favoriteWaltz = "data/Beethoven/Beethoven_Fav_Waltz";
	private String favWalAudio ="data/Beethoven/BeethFavAudio.mp3";

	private String moonlight = "data/Beethoven/Beethoven_Moonlight_Sonata";
	private String moonAudio = "data/Beethoven/Moonlight_Sonata.mp3";
	
	private boolean furElBool;
	private boolean favWalBool; 
	private boolean sonatBool;
	
	private int furElCount;
	private int favWalCount;
	private int sonatCount;

	/**
	 * Represents the screen for user interaction
	 * @param surface is the PApplet object used to draw the screen
	 */
	public BeethovenScreen(DrawingSurface surface)
	{
		super(800,600);
		this.surface = surface;
		song1 = new Rectangle(800/2-100,600/2-200,200,85);
		song2 = new Rectangle(800/2-100,600/2-50,200,85);
		song3 = new Rectangle(800/2-100,600/2 + 100,200,85);
		composeButton = new Rectangle(600,500,125,75);
		furElBool = false;
		favWalBool = false;
		sonatBool = false;
		furElCount = 0;
		favWalCount = 0;
		sonatCount = 0;
	}

	/**
	 * The graphics on the screen
	 */
	public void draw() {

		surface.pushStyle();
		
		surface.background(255,255,255);
		
		surface.fill(surface.getLavender().getRGB());
		surface.rect(song1.x, song1.y, song1.width, song1.height, 10);
		surface.fill(surface.getMint().getRGB());
		surface.rect(song2.x, song2.y, song2.width, song2.height, 10);
		surface.fill(surface.getLavender().getRGB());
		surface.rect(song3.x, song3.y, song3.width, song3.height, 10);
		surface.fill(surface.getMint().getRGB());
		surface.rect(composeButton.x, composeButton.y, composeButton.width, composeButton.height, 10);
		surface.fill(0);
		String str = "Fur Elise!";
		String str2 = "Favorite Waltz!";
		String str3 = "Moonlight";
		String str3p3 = "Sonata!";
		String composeStr = "Compose";
		float w = surface.textWidth(str);
		surface.textSize(20);
		surface.text(composeStr, composeButton.x+ composeButton.width/2 - 50, composeButton.y+composeButton.height/2+5);
		surface.textSize(25);
		surface.text(str, song1.x+song1.width/2-w/2, song1.y+song1.height/2+5);
		surface.text(str2, song2.x+song2.width/2-w/2-27, song2.y+song2.height/2+7);
		surface.text(str3, song3.x + song3.width/3 - 20, song3.y+song3.height/2);
		surface.text(str3p3, song3.x + song3.width/3 - 20, song3.y+song3.height/2 + 25);
		
		if(furElBool)
		{
			surface.textSize(15);
			surface.text("You've selected Fur Elise", song1.x + song1.width + 20, song1.y + song1.width/4);
		}
		if(favWalBool)
		{
			surface.textSize(15);
			surface.text("You've selected Favorite Waltz", song2.x + song2.width + 20, song2.y + song2.width/4);
		}
		if(sonatBool)
		{
			surface.textSize(15);
			surface.text("You've selected Moonlight Sonata", song3.x + song3.width + 20, song3.y + song3.width/4);
		}

		surface.popStyle();
	}
 
	/**
	 * What happens when the user interacts with the screen
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (song1.contains(p))  
		{
			if(furElCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(furElise);
				furElBool = true;
				surface.setCurrentPlaying(furEliseAudio);
				surface.playSong(furEliseAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(furEliseAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(furElise);	
				furElBool = false;
			}
			furElCount++;
		}
		
		if (song2.contains(p)) 
		{
			if(favWalCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(favoriteWaltz);
				favWalBool = true;
				surface.setCurrentPlaying(favWalAudio);
				surface.playSong(favWalAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(favWalAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(favoriteWaltz);	
				favWalBool = false;
			}
			favWalCount++;
		}
		
		if (song3.contains(p))  
		{
			if(sonatCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(moonlight);
				sonatBool = true;
				surface.setCurrentPlaying(moonAudio);
				surface.playSong(moonAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(moonAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(moonlight);	
				sonatBool = false;
			}
			sonatCount++;
		}
		if (composeButton.contains(p))  
		{
			surface.finishedComposer();
			surface.stopSong();
			surface.switchScreen(4);
			//surface.compose();
	
		}
	}


}

