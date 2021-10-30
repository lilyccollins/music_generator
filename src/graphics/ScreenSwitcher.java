package graphics;
/**
 * Switches between screens
 */
public interface ScreenSwitcher 
{
	public static final int SCREEN1 = 0;
	public static final int SCREEN2 = 1;
	
	public void switchScreen(int i);
}
