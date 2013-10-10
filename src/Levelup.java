import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Levelup  extends JFrame implements ActionListener {

	private int whichplayer = 0;
	private JButton okay;
	
	public Levelup (int player) {
	
		super ("Level Aufgestiegen");
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(1,1));  

		JLabel levelup = new JLabel("Level aufgestiegen!");
		JButton okay = new JButton("Okay");
		
		okay.addActionListener(this);
		
		JPanel panel = new JPanel(new GridLayout (1,2));
		
		panel.add(levelup);
		panel.add(okay);
		
		whichplayer = player;
		
		this.getContentPane().add(panel);

	    this.pack();
	    this.setVisible(true);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.okay) 	{if (whichplayer == 1 && Game.player1.cannontype <=3) Game.player1.cannontype += 1;
											 if (whichplayer == 2 && Game.player2.cannontype <=3) Game.player2.cannontype += 1;
											 else System.out.println("maximales Level erreicht");
											 Game.running = true;
											 this.dispose();
											 }
	}
}
