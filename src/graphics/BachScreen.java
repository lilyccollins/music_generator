package graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Represents the screen shown when the user selects Bach
 *
 */
public class BachScreen extends Screen {
	private DrawingSurface surface;
	
	private Rectangle song1;
	private Rectangle song2; // buttons for each song
	private Rectangle song3;
	private Rectangle composeButton;

	private String menuetTrio = "data/Bach/menuet-trio_3V";
	private String trioAudio = "data/Bach/trioAudio.mp3";
	
	private String celloSuite = "data/Bach/cello_suite";
	private String celloAudio = "data/Bach/celloAudio.mp3";
	
	private String prelude = "data/Bach/prelude_C_Maj";
	private String preludeAudio = "data/Bach/preludeAudio.mp3";

	private boolean trioBool;
	private boolean celloBool;
	private boolean preludeBool;
	
	private int trioCount;
	private int celloCount;
	private int preludeCount;
	
	/**
	 * Represents the screen for user interaction
	 * @param surface is the PApplet object used to draw the screen
	 */
	public BachScreen(DrawingSurface surface) 
	{
		super(800,600);
		this.surface = surface;
		
		song1 = new Rectangle(800/2-100,600/2-200,200,100);
		song2 = new Rectangle(800/2-100,600/2-50,200,100);
		song3 = new Rectangle(800/2-100,600/2 + 100,200,100);
		composeButton = new Rectangle(600,500,125,75);
		
		trioBool = false;
		celloBool = false;
		preludeBool = false;
		
		trioCount = 0;
		celloCount = 0;
		preludeCount = 0;
	}

	/**
	 * The graphics on the screen
	 */
	public void draw() {
 
		surface.pushStyle();
		
		surface.background(255,255,255);
		
		surface.fill(surface.getPeriwinkle().getRGB());
		surface.rect(song1.x, song1.y, song1.width, song1.height, 10);
		surface.fill(surface.getLavender().getRGB());
		surface.rect(song2.x, song2.y, song2.width, song2.height, 10);
		surface.fill(surface.getPeriwinkle().getRGB());
		surface.rect(song3.x, song3.y, song3.width, song3.height, 10);
		surface.fill(surface.getLavender().getRGB());
		surface.rect(composeButton.x, composeButton.y, composeButton.width, composeButton.height, 10);

		surface.fill(0);
		String str = "Menuet Trio!";
		String str2 = "Cello Suite!";
		String str3 = "Prelude C Major!";
		String composeStr = "Compose";
		float w = surface.textWidth(str);
		surface.textSize(20);
		surface.text(composeStr, composeButton.x+ composeButton.width/2 - 50, composeButton.y+composeButton.height/2+5);
		surface.textSize(25);
		surface.text(str, song1.x+song1.width/2-w/2, song1.y+song1.height/2+ 5);
		surface.text(str2, song2.x+song2.width/2-w/2, song2.y+song2.height/2 + 5);
		surface.text(str3, song3.x , song3.y+song3.height/2 + 5);
		if(trioBool)
		{
			surface.textSize(15);
			surface.text("You've selected Menuet Trio!", song1.x + song1.width + 20, song1.y + song1.width/4);
		}
		if(celloBool)
		{
			surface.textSize(15);
			surface.text("You've selected Cello Suite!", song2.x + song2.width + 20, song2.y + song2.width/4);
		}
		if(preludeBool)
		{
			surface.textSize(15);
			surface.text("You've selected Prelude in C Major!", song3.x + song3.width + 20, song3.y + song3.width/4);
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
			if(trioCount % 2 == 0)
			{	
				surface.stopSong();
				surface.addSong(menuetTrio);
				trioBool = true;
				surface.setCurrentPlaying(trioAudio);
				surface.playSong(trioAudio);
			}
			else 
			{
				if(surface.getCurrentPlaying().equals(trioAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(menuetTrio);
				trioBool = false;
			}
			trioCount++;
		}
		if (song2.contains(p))  
		{
			if(celloCount % 2 == 0)
			{	
				surface.stopSong();
				surface.addSong(celloSuite);
				celloBool = true;
				surface.setCurrentPlaying(celloAudio);
				surface.playSong(celloAudio);
			}
			else 
			{
				if(surface.getCurrentPlaying().equals(celloAudio))
				{
					surface.stopSong();
				}
				surface.removeSong(celloSuite);
				celloBool = false;
			}
			celloCount++;
		}
		if (song3.contains(p))  
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
		if (composeButton.contains(p))  
		{
			surface.finishedComposer();
			surface.stopSong();
			surface.switchScreen(4);
			//surface.compose();
		}
	}
	

}

