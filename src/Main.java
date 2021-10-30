import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import graphics.DrawingSurface;
import processing.core.PApplet;


/**
 * Composes a new song in an output file as a text file
 */
public class Main extends JPanel implements ActionListener {

	private JFrame window;
	private Object selectedComposer;
	private JComboBox<String> cb;
	private JLabel lbl;
	private JButton btn;
	private JPanel panel;
	private static DrawingSurface drawing;
	private static final String BEETHOVEN = "Beethoven";
	private static final String CHOPIN = "Chopin";
	private static final String BACH = "Bach";
	private static final String LALALAND = "La La Land Soundtrack";

	/**
	 * Represents the window and buttons in which the program takes place
	 *
	 */
	public Main()
	{
		
		window = new JFrame("Songwriter");
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 500);
		window.setLocation(430, 100);
		
		panel = new JPanel();
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
//		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		window.add(panel);

		lbl = new JLabel("Select an artist and click OK");
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
//		lbl.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		panel.add(lbl);

		String[] choices = { BEETHOVEN, CHOPIN, BACH, LALALAND};
  

		cb = new JComboBox<String>(choices); 
		cb.setMaximumSize(cb.getPreferredSize());
		cb.setAlignmentX(Component.CENTER_ALIGNMENT);
		cb.setSelectedItem(null);
		cb.setEditable(true);
		panel.add(cb);
		
		btn = new JButton("OK");
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btn);
		

		JLabel L1 = new JLabel("Welcome to our program! This program generates new music based");
		JLabel L2 = new JLabel("on the songs that you choose. Click the menu below to choose");
		JLabel L3 = new JLabel("the composer for your music. You may choose as many songs as");
		JLabel L4 = new JLabel("you'd like. The music is shown in ABC notation, not in the");
		JLabel L5 = new JLabel("common sheet music format.");
		L1.setAlignmentX(CENTER_ALIGNMENT);
		L2.setAlignmentX(CENTER_ALIGNMENT);
		L3.setAlignmentX(CENTER_ALIGNMENT); 
		L4.setAlignmentX(CENTER_ALIGNMENT);
		L5.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.add(L1);
		panel.add(L2);
		panel.add(L3);
		panel.add(L4);
		panel.add(L5);

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cb.getSelectedItem() != null)
				{
					selectedComposer = cb.getSelectedItem();
//					System.out.println("" + selectedComposer);
					drawing = new DrawingSurface(selectedComposer.toString());
					PApplet.runSketch(new String[]{""}, drawing);
					window.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null,  "Please select composer");
				}
			}
		});



		window.setVisible(true);
	}

	/**
	 * Creates the window and buttons in which the program takes place
	 *
	 */
	public static void main(String[] args)
	{
		Main m = new Main();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}		



}
