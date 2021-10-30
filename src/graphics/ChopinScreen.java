package graphics;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;

/**
 * Represents the screen shown when the user selects Chopin
 *
 */
public class ChopinScreen extends Screen {
	
	private DrawingSurface surface;
	
	private Rectangle song1;
    private Rectangle song2; // buttons for each song
	private Rectangle song3;
	
	private String mazurka = "data/Chopin/mazurka_C4V";
	private String mazurkaAudio = "data/Chopin/mazurkaAudio.mp3";
	
	private String prelude = "data/Chopin/prelude_C2V";
	private String preludeAudio = "data/Chopin/preludeAudio.mp3";
	
	private String waltz = "data/Chopin/waltz_1V";
	private String waltzAudio = "data/Chopin/waltzAudio.mp3";
	
	
	private boolean mazurkaBool;
	private boolean preludeBool;
	private boolean waltzBool;
	
	private int mazurkaCount;
	private int preludeCount;
	private int waltzCount;

	private Rectangle composeButton;
	
	/**
	 * Represents the screen for user interaction
	 * @param surface is the PApplet object used to draw the screen
	 */
	public ChopinScreen(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		
		song1 = new Rectangle(800/2-100,600/2-200,200,85);
		song2 = new Rectangle(800/2-100,600/2-50,200,90);
		song3 = new Rectangle(800/2-100,600/2 + 100,200,90);
		composeButton = new Rectangle(600,500,125,75);
		
		mazurkaBool = false; 
		preludeBool = false;
		waltzBool = false;
		
		mazurkaCount = 0;
		preludeCount = 0;
		waltzCount = 0;

	}

	/**
	 * The graphics on the screen
	 */
	public void draw() {
 
		surface.pushStyle();
		
		surface.background(255,255,255);
		
		surface.fill(surface.getPink().getRGB());
		surface.rect(song1.x, song1.y, song1.width, song1.height, 10);
		surface.fill(surface.getMint().getRGB());
		surface.rect(song2.x, song2.y, song2.width, song2.height, 10);
		surface.fill(surface.getPink().getRGB());
		surface.rect(song3.x, song3.y, song3.width, song3.height, 10);
		surface.fill(surface.getMint().getRGB());
		surface.rect(composeButton.x, composeButton.y, composeButton.width, composeButton.height, 10);
		
		surface.fill(0);
		String str = "Mazurka!";
		String str2 = "Prelude!";
		String str3 = "Waltz!";
		String composeStr = "Compose";
		float w = surface.textWidth(str);	
		surface.textSize(20);
		surface.text(composeStr, composeButton.x+ composeButton.width/2 - 50, composeButton.y+composeButton.height/2+5);
		surface.textSize(25);
		surface.text(str, song1.x+song1.width/2-w/2, song1.y+song1.height/2 + 5);
		surface.text(str2, song2.x+song2.width/2-w/2 + 5, song2.y+song2.height/2 + 5);
		surface.text(str3, song3.x+song3.width/2-w/2 + 10, song3.y+song3.height/2 + 5);
		if(mazurkaBool)
		{
			surface.textSize(15);
			surface.text("You've selected Mazurka!", song1.x + song1.width + 20, song1.y + song1.width/4);
		}
		if(preludeBool)
		{ 
			surface.textSize(15);
			surface.text("You've selected Prelude!", song2.x + song2.width + 20, song2.y + song2.width/4);
		}
		if(waltzBool)
		{
			surface.textSize(15);
			surface.text("You've selected Waltz!", song3.x + song3.width + 20, song3.y + song3.width/4);
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
			if(mazurkaCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(mazurka);
				mazurkaBool = true;
				surface.setCurrentPlaying(mazurkaAudio);
				surface.playSong(mazurkaAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(mazurkaAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(mazurka);	
				mazurkaBool = false;
			}
			mazurkaCount++;
		}	
		if (song2.contains(p)) 
		{
			if(preludeCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(prelude);
				preludeBool = true;
				surface.setCurrentPlaying(preludeAudio);
				surface.playSong(preludeAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(preludeAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(prelude);	
				preludeBool = false;
			}
			preludeCount++;
		}	
		if (song3.contains(p)) 
		{
			if(waltzCount % 2 == 0)
			{
				surface.stopSong();
				surface.addSong(waltz);
				waltzBool = true;
				surface.setCurrentPlaying(waltzAudio);
				surface.playSong(waltzAudio);
			}
			else
			{
				if(surface.getCurrentPlaying().equals(waltzAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(waltz);	
				waltzBool = false;
			}
			waltzCount++;
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