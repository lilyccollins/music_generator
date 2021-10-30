package composer;

/**
 * Represents the index of a note in sheet music
 *
 */
public class Index extends FrequencyTable {
	private int id;
	
	/**
	 * Creates a index to keep track of which notes occur at a given index into the songs selected
	 * @param the index in the song
	 */
	public Index(int id) {
		super();
		this.id = id;
	}
	
	/**
	 * Gets the value of the index in the song
	 * @return the index
	 */
	public int getID() {
		return id;
	}
	
}
