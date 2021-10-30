package composer;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Lily Collins
 * Reads and processes text files of music in ABC notation
 */
public class Sheet {
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static final String PREFIXES = "^=_";
	private static final String SUFFIXES = "0123456789/,'";
	private static final String LETTERS = "ABCDEFGabcdefgz";
	private static final String STRUCTURES = "[]"; //:||:
	private static final String FIELDS = "XTCOAMLQPZNGHKRBDFSIVUW";
	
	
	private String filename;
	private ArrayList<String> notes;
	
	private String title;
	private String composer;
	private String meter;
	private String noteLength;
	private String tempo;
	private String keySig;
	
	
	/**
	 * Initializes the Sheet with the given filename
	 * @param filename the music chosen
	 */
	public Sheet(String filename) {
		this.filename = filename;
		notes = new ArrayList<String>();
	}
	
	
	/**
	 * Reads a text file in ABC notation, adds each string note to an Arraylist<String> of notes, and stores the information in its header fields
	 */
	public void readFile() {
		FileReader reader;
		Scanner in = null;
		String str = "";
		try {
			reader = new FileReader(filename);
			in = new Scanner(reader);
			while (in.hasNext()) {
				str += in.nextLine() + LINE_SEPARATOR;
			}
			str = str.substring(0, str.length()-1);
		}
		catch (IOException ex) {
			System.out.println("Data file " + filename + " cannot be read.");
		}
		finally {
			if (in != null)
				in.close();
		}
		if (str.isEmpty()) {
			throw new IllegalArgumentException("Data file " + filename + " is empty.");
		}
		
		//deal with fields here
		title = getMultipleFields(str, "T:");
		composer = getSingleField(str, "C:");
		meter = getSingleField(str, "M:");
		noteLength = getSingleField(str, "L:");
		tempo = getSingleField(str, "Q:");
		keySig = getSingleField(str, "K:");
		
		//handle repeats and strip the string
		str = preProcess(getMultipleFields(str, "V:1"));
		
		//break str into Notes
		int start = 0;
		while ( start < str.length()) {
			String note = "";
			int i = 0;
			
			//is a chord
			if (str.charAt(start) == '[') {
				while (str.charAt(start+i-1) != ']') {
					
					System.out.println(i);
					
					note += str.charAt(i+start);
					i++;
				}
			}
			
			//is not a chord
			else {
				//add prefixes
				while (isPrefix(str.charAt(i+start))) {
					note += str.charAt(i+start);//don't parse twice
					i++;
				}
				//add letter
				if (i+start < str.length() && isLetter(str.charAt(i+start))) {
					note += str.charAt(i+start);
					i++;
				}
				//add sufixes
				while (i+start < str.length() && isSuffix(str.charAt(i+start))) {
					note += str.charAt(i+start);
					i++;
				}				
			}
			
			
			
			if (note.isEmpty()) {
				start++;
			}
			else {
				notes.add(note);
				start += i;
			}
		}
	}

	
	/**
	 * Returns a String with the Arraylist of notes and field information in ABC format
	 * @return a String of sheet music in ABC format
	 */
	public String getABCNotation() {
		String abc = "T:Songwriter Generated Music"
				+ "\nC:Songwriter Program by Lily Collins, Jenny Ngo, Frances Jing"
				+ "\nM:" + meter
				+ "\nL:" + noteLength
				+ "\nQ:" + tempo
				+ "\nK:" + keySig
				+ "\nV:1\n";
		
		//find the number of notes per measure num
		int notesPerMeasure;
		if (!meter.isEmpty() && !noteLength.isEmpty()) {
			double m = (double)(Integer.parseInt(meter.substring(0, meter.indexOf('/')))) / Integer.parseInt(meter.substring(meter.indexOf('/') + 1));
			double l = (double)(Integer.parseInt(noteLength.substring(0, noteLength.indexOf('/')))) / Integer.parseInt(noteLength.substring(meter.indexOf('/') + 1));
			notesPerMeasure = (int)(m/l + 0.5);
		}
		else {
			notesPerMeasure = 6;//default
		}
		//adds notes to string with spaces and measure bars
		int n = 0;
		for (String note : notes) {
			//finds the length of the note
			int length = 1;
			for (int i = 0; i < note.length(); i++) {
				char a = note.charAt(i);
				if (Character.isDigit(a)) {
					length = Integer.parseInt(a + "");
				}
			}
			n += length;
			abc += note + " ";
			if (n%notesPerMeasure == 0) {
				abc += "| ";
			}
		}
		return abc;
	}
	
	
	/**
	 * Sets the ArrayList<String of notes in the Sheet to the given ArrayList<String>
	 * @param ArrayList<String of notes in for the Sheet
	 */
	public void setNotes(ArrayList<String> n) {
		notes = n;
	}
	
	
	/**
	 * Sets the title of the Sheet to the given String
	 * @param the title of the Sheet music
	 */
	public void setTitle(String t) {
		title = t;
	}
	
	
	/**
	 * Sets the composer of the Sheet to the given String
	 * @param the composer of the Sheet music
	 */
	public void setComposer(String t) {
		composer = t;
	}
	
	
	/**
	 * Sets the meter of the Sheet to the given String
	 * @param the meter of the Sheet music
	 */
	public void setMeter(String t) {
		meter = t;
	}
	
	
	/**
	 * Sets the default note length of the Sheet to the given String
	 * @param the default note length of the Sheet music
	 */
	public void setNoteLength(String t) {
		noteLength = t;
	}
	
	
	/**
	 * Sets the tempo of the Sheet to the given String
	 * @param the tempo of the Sheet music
	 */
	public void setTempo(String t) {
		tempo = t;
	}
	
	
	/**
	 * Sets the key signature of the Sheet to the given String
	 * @param the key signature of the Sheet music
	 */
	public void setKeySig(String t) {
		keySig = t;
	}
	
	
	/**
	 * Gets the ArrayList of the notes in the sheet
	 * @return ArrayList<String> of the notes in the sheet
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	
	/**
	 * Returns the title of the Sheet music or untitled if none is provided
	 * @return the title of the Sheet music
	 */
	public String getTitle() {
		if (title.isEmpty()) {
			return "untitled";
		}
		else {
			return title;
		}
	}
	
	/**
	 * Returns the composer of the Sheet music or unknown if none is provided
	 * @return the composer of the Sheet music
	 */
	public String getComposer() {
		if (composer.isEmpty()) {
			return "unknown";
		}
		else {
			return composer;
		}
	}
	
	
	/**
	 * Returns the meter of the Sheet music or an empty String if none is provided
	 * @return the meter of the Sheet music
	 */
	public String getMeter() {
		return meter;
	}
	
	
	/**
	 * Returns the default note length of the Sheet music or an empty String if none is provided
	 * @return the default note length of the Sheet music
	 */
	public String getNoteLength() {
		return noteLength;
	}
	
	
	/**
	 * Returns the tempo of the Sheet music or an empty String if none is provided
	 * @return the tempo of the Sheet music
	 */
	public String getTempo() {
		return tempo;
	}
	
	
	/**
	 * Returns the key signature of the Sheet music or an empty String if none is provided
	 * @return the key signature of the Sheet music
	 */
	public String getKeySig() {
		return keySig;
	}
	
	
	
	//handles repeats and strips the string
	private String preProcess(String s) {
		//will have to edit once we can deal with fields so that it can account for the last repeat (without a final |: )
		
		String p = "";
		
		//handle repeats
		if (s.contains(":|")) {
			
			String repeat = "";
			for (int i = 0; i < s.length() - 1; i++) {
				
				if (isRepeatStart(s.substring(i, i+2))) {
					int nextColon = s.indexOf(':', i);
					//if its the last repeat
					if (nextColon < 0) {
						nextColon = s.length();
						p += s.substring(i+2);
					}
					//it it is not the last repeat
					else {
						p += repeat + s.substring(i+2, nextColon);
					}
					if (s.charAt(nextColon - 1) == '|') {
						repeat = "";
					}
					i = nextColon;
				}
				else {
					p += s.charAt(i);
					repeat += s.charAt(i);
				}
			}
		}
		else {
			p = s;
		}
		
		//strip
		String stripped = "";
		for (int i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			if (isLetter(c) || isSuffix(c) || isPrefix(c) || isStructure(c)) {
				stripped += c;
			}
		}
		//check if stripped got rid of the whole thing
		if (stripped.isEmpty()) {
			throw new IllegalArgumentException("Data file " + filename + " does not contain ABC notation music.");
		}
		return stripped;
	}
	
	private String getMultipleFields(String str, String fieldKey) {
		String f = "";
		int i = 0;
		while (str.indexOf(fieldKey, i) != -1) {			
			int indexOfF = str.indexOf(fieldKey, i);
			int indexOfNext = indexOfFieldHeader(str, indexOfF + 1);
			//check if its the last field
			if (indexOfNext == -1) {
				indexOfNext = str.length();
			}
			String sub = str.substring(indexOfF + fieldKey.length(), indexOfNext);
			//if field key is voice and sub is not music
			if (fieldKey.contains("V") && !sub.contains("|")) {
				sub = "";
			}
			f += sub + " ";
			i = indexOfNext;
		}
		return f;
	}
	
	private String getSingleField(String str, String fieldKey) {
		String f = "";
		if (str.indexOf(fieldKey) != -1) {			
			int indexOfF = str.indexOf(fieldKey);
			int indexOfN = str.indexOf(LINE_SEPARATOR, indexOfF);
			//check if its the last field
			if (indexOfN == -1) {
				indexOfN = str.length();
			}
			f += str.substring(indexOfF + fieldKey.length(), indexOfN);
		}
		
		return f;
	}
	
	private int indexOfFieldHeader(String str, int from) {
		for (int i = from; i < str.length() - 1; i++) {
			if (str.charAt(i+1) == ':' && FIELDS.contains(str.charAt(i) + "")) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean isPrefix(char c) {
		//prefixes are ^ = _
		return (PREFIXES.contains(c+""));
	}
	
	private boolean isSuffix(char c) {
		//suffixes are numbers / , '
		return (SUFFIXES.contains(c+""));
	}
	
	private boolean isLetter(char c) {
		//capital and lowercase a-g and lowercase z (rest)
		return (LETTERS.contains(c+""));
	}
	
	private boolean isStructure(char c) {
		return (STRUCTURES.contains(c+""));
	}
	
	private boolean isRepeatStart(String s) {
		return ((s.charAt(0) == '[' || s.charAt(0) == '|') && Character.isDigit(s.charAt(1)));
	}
		
}
