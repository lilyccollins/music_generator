package graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import processing.sound.SoundFile;



/**
 * Represents the screen shown when the user selects La La Land Soundtrack
 *
 */
public class LaLaLandScreen extends Screen
{
	
	private DrawingSurface surface;
	private Rectangle song1;
    private Rectangle song2; // buttons for each song
//	private Rectangle song3;	
    
	private Rectangle composeButton;
    private int cityCount;
    private int themeCount;
    
	private String cityOfStars = "data/LaLaLand/CityOfStars_1V_NoFields";
	private String MSTheme = "data/LaLaLand/MiaSebTheme_1V_NoFields";
	private String MSThemeAudio = "data/LaLaLand/MSThemeAudio.mp3";
	private String cityStarsAudio = "data/LaLaLand/CityOfStarsAudio.mp3";
	
	private boolean cityBool;
	private boolean themeBool;
	
	/**
	 * Represents the screen for user interaction
	 * @param surface is the PApplet object used to draw the screen
	 */
	public LaLaLandScreen(DrawingSurface surface) 
	{
		super(800,600);
		this.surface = surface;
		song1 = new Rectangle(800/2-100,600/2-200,200,85);
		song2 = new Rectangle(800/2-100,600/2-50,200,90);
//		song3 = new Rectangle(800/2-100,600/2 + 100,200,90);
		composeButton = new Rectangle(600,500,125,75);
		cityBool = false;
		themeBool = false;
		cityCount = 0;
		themeCount = 0;


	}

	/**
	 * The graphics on the screen
	 */
	public void draw() {

		surface.pushStyle();
		
		surface.background(255,255,255);
		
		surface.fill(surface.getLavender().getRGB());
		surface.rect(song1.x, song1.y, song1.width, song1.height, 10);
		surface.fill(surface.getPink().getRGB());
		surface.rect(song2.x, song2.y, song2.width, song2.height, 10);
//		surface.rect(song3.x, song3.y, song3.width, song3.height, 10);

		surface.fill(surface.getLavender().getRGB());
		surface.rect(composeButton.x, composeButton.y, composeButton.width, composeButton.height, 10);
		surface.fill(0);
		String str = "City Of Stars!";
		String str2 = "Mia and";
		String str2p2 = "Sebastian's Theme!";
//		String str3 = "Waltz!";
		String composeStr = "Compose";
		float w = surface.textWidth(str);	
		surface.textSize(20);
		surface.text(composeStr, composeButton.x+ composeButton.width/2 - 50, composeButton.y+composeButton.height/2+5);
		surface.textSize(25);
		surface.text(str, song1.x+song1.width/2-w/2 - 10, song1.y+song1.height/2+5);
		surface.textSize(20);
		surface.text(str2, song2.x+song2.width/2-w/2 + 15, song2.y+song2.height/2 - 5);
		surface.text(str2p2, song2.x + 10, song2.y+song2.height/2 + 15);
//		surface.text(str3, song3.x+song3.width/2-w/2, song3.y+song3.height/2);
		
		if(cityBool)
		{
			surface.textSize(15);
			surface.text("You've selected City Of Stars!", song1.x + song1.width + 20, song1.y + song1.width/4);
		}
		if(themeBool)
		{
			surface.textSize(15);
			surface.text("You've selected Mia and", song2.x + song2.width + 20, song2.y + song2.width/4);
			surface.text("Sebastian's Theme!", song2.x + song2.width + 20, song2.y + song2.width/4 + 15);
		}

		surface.popStyle();
	}

	/**
	 * What happens when the user interacts with the screen
	 */
	public void mousePressed() 
	{
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));


		if (song1.contains(p)) 
		{
			if(cityCount % 2 == 0)
			{	
				surface.stopSong();
				surface.addSong(cityOfStars);
				cityBool = true;
				surface.setCurrentPlaying(cityStarsAudio);
				surface.playSong(cityStarsAudio);
			}
			else 
			{
				if(surface.getCurrentPlaying().equals(cityStarsAudio))
				{
					surface.stopSong(); 
				}
				surface.removeSong(cityOfStars);
				cityBool = false;
			}
			cityCount++;

		}	
		if (song2.contains(p)) 
		{
			
			if(themeCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(MSTheme);
				themeBool = true;
				surface.setCurrentPlaying(MSThemeAudio);
				surface.playSong(MSThemeAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(MSThemeAudio))
				{
					surface.stopSong();
				} 
				surface.removeSong(MSTheme);	
				themeBool = false;
			}
			themeCount++;

		}	

		if (composeButton.contains(p))  
		{
			surface.finishedComposer();
			surface.stopSong();
			surface.switchScreen(4);
		}
	}
	
}





