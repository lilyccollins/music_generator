package graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import composer.Composer;
import composer.Sheet;
import processing.core.PApplet;
import processing.sound.SoundFile;


/**
 * Changes the window and buttons in which the program takes place
 *
 */
public class DrawingSurface extends PApplet implements ScreenSwitcher
{

	public float ratioX, ratioY;

	private ArrayList<Integer> keys;
//	private boolean isLoading;
//	private Rectangle loadBar;
	
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private ArrayList<String> filenames;
	private Sheet newSheet;
	private SoundFile file;
	private String currentPlaying;
	private final Color PERIWINKLE;
	private final Color LAVENDER;
	private final Color ROSE;
	private final Color MINT;
//	private final Color loadColor; 
	
	
	/**
	 * Initializes the composer screen
	 * @param composer is the selected composer
	 */
	public DrawingSurface(String composer) 
	{

		screens = new ArrayList<Screen>();
//		isLoading = false;
//		loadBar = new Rectangle(800/2-175,500,350,25);
		
		keys = new ArrayList<Integer>();
		filenames = new ArrayList<String>();

		BeethovenScreen beeth = new BeethovenScreen(this);
		screens.add(beeth); 

		ChopinScreen chop = new ChopinScreen(this);
		screens.add(chop);

		BachScreen bach = new BachScreen(this);
		screens.add(bach);
		
		LaLaLandScreen lala = new LaLaLandScreen(this);
		screens.add(lala);

		OutputScreen out = new OutputScreen(this);
		screens.add(out);
		
		if(composer.contentEquals("Beethoven"))
		{
			activeScreen = screens.get(0);
		}
		else if(composer.contentEquals("Chopin"))
		{
			activeScreen = screens.get(1);
		} 
		else if(composer.contentEquals("Bach"))
		{
			activeScreen = screens.get(2);
		}
		else if(composer.contentEquals("La La Land Soundtrack"))
		{
			activeScreen = screens.get(3);
		}
		
		this.PERIWINKLE = new Color(220, 241, 251);
		this.LAVENDER = new Color(230,230,250);
		this.ROSE = new Color(255,240,245);
		this.MINT = new Color(220, 245, 208);
//		this.loadColor = new Color(255,223,186);
	}

	/**
	 * Shows song options the user can choose
	 * @return the song the user chooses
	 */
	public ArrayList<String> getFilename()
	{
		return this.filenames; 
	}

	/**
	 * Creates a new Sheet of new music based off on the input song
	 * @param song the song to be based off on
	 */

	public void addSong(String song)
	{
//		isLoading = true;
		if(!filenames.contains(song))
		{
			filenames.add(song);
//			isLoading = false;
		}
		
	}
	
	/**
	 * @return Lavender color field
	 */
	public Color getLavender()
	{
		return this.LAVENDER;
	}
	
	/**
	 * @return Periwinkle color field
	 */
	public Color getPeriwinkle()
	{
		return this.PERIWINKLE;
	}
	
	/**
	 * @return Mint color field
	 */
	public Color getMint()
	{
		return this.MINT;
	}
	
	/**
	 * @return Rose color field
	 */
	public Color getPink()
	{
		return this.ROSE;
	}
	
	/**
	 * Creates a new Sheet of new music based off on the input song
	 * @param song the song to be based off on
	 */

	public void removeSong(String song)
	{
		if(filenames.size() != 0)
		{
			filenames.remove(filenames.indexOf(song));
		}
	}
	
	/**
	 * Sets the currently playing song
	 * @param song is the song to be played
	 */
	public void setCurrentPlaying(String song)
	{
		currentPlaying = song;
	}
	
	/**
	 * @return the audio that is currently playing
	 */
	public String getCurrentPlaying()
	{
		return currentPlaying;
	}

	
	/**
	 * Pauses audio
	 */
	public void stopSong()
	{
		if(file != null && file.isPlaying())
		{
			file.stop();
		}
	}
	
//	public void stopSong(String song)
//	{
//		SoundFile inputSongFile = new SoundFile(this,song);
//		if(file.equals(inputSongFile) && file.isPlaying())
//		{
//			file.stop();
//		}
//	}
	/**
	 * Plays audio for inputted song
	 * @param song is the string for the path of the song
	 */
	public void playSong(String song)
	{
		file = new SoundFile(this, song);
		file.play();
	}
	
	/**
	 * Composes the output piece for all the final song choices
	 * @return Composer object with all the pieces
	 */
	public Composer finishedComposer()
	{
		Composer c = new Composer(filenames);
		c.tally();
		Sheet newMusic = c.compose(100);
		newSheet = newMusic;
		System.out.println(newSheet.getABCNotation());
		return c;
		
	}
	
	/**
	 * Gets the new music Sheet 
	 * @return the sheet
	 */
	public Sheet getNewSheet() 
	{
		return newSheet;
	}

	/**
	 * Gets the size or length of the filename
	 * @return the size
	 */
	public int getFilenameSize()
	{
		return filenames.size();
	}

	/**
	 * Makes the dimensions of the screen
	 */
	public void settings() {
		// size(DRAWING_WIDTH, DRAWING_HEIGHT, P2D);
		size(activeScreen.DRAWING_WIDTH, activeScreen.DRAWING_HEIGHT);
	}

	/**
	 * Makes the screens resizable
	 */
	public void setup() 
	{
		surface.setResizable(true);
		for (Screen s : screens)
			s.setup();
	} 

	/**
	 * Draws on the program window
	 */
	public void draw() 
	{
		ratioX = (float)width/activeScreen.DRAWING_WIDTH;
		ratioY = (float)height/activeScreen.DRAWING_HEIGHT;

		pushMatrix();

		scale(ratioX, ratioY);

		activeScreen.draw();
		
//		if(isLoading) 
//		{
//			fill(loadColor.getRGB());
//			rect(loadBar.x,loadBar.y,loadBar.width, loadBar.height);
//		}
//		file = new SoundFile(this, "data/LaLaLand/MSThemeAudio.mp3");
//		file.cue(0);
//		file.play();
		popMatrix();
	}

	/**
	 * Finds the coordinates of a point
	 * @param actual is the point
	 * @return coordinates of the point
	 */
	public Point actualCoordinatesToAssumed(Point actual) 
	{
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	/**
	 * Switches the screen displayed in the window
	 */
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}
	
	
	/**
	 * Calls the actions included in the screen's mousePressed() method
	 */
	public void mousePressed() {
		activeScreen.mousePressed();
	}

}
