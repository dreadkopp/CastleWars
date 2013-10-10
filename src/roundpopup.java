import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class roundpopup extends JFrame implements ActionListener {

	private JButton nocheinmal;

	
	public roundpopup () {
		super ("Ende");
		this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(1,1));  

        JLabel neuerunde = new JLabel("Rundenende");
        JButton nocheinmal = new JButton("Noch eine Runde");
        
        nocheinmal.addActionListener(this);
        
        JPanel panel = new JPanel(new GridLayout (1,2));
        
        panel.add(neuerunde);
        panel.add(nocheinmal);
        
        this.getContentPane().add(panel);

        this.pack();
        this.setVisible(true);
        
        
    
    	
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.nocheinmal ) Game.newround (); this.dispose();
		
	}
}
