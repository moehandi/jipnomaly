package sistem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ProgressBar extends JFrame implements  ActionListener
 {
	private	JProgressBar    progress;
	private	JButton         button;
	private	JLabel          label1;
	private	JPanel          mainPanel;
        
	public ProgressBar()
	{
            
		setTitle("Loading process");
		setSize(700, 350);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension frameSize = this.getSize();
                if (frameSize.height > screenSize.height) {
                    frameSize.height = screenSize.height;
                }
                if (frameSize.width > screenSize.width) {
                    frameSize.width = screenSize.width;
                }
                this.setLocation((screenSize.width - frameSize.width) / 2,
                        (screenSize.height - frameSize.height) / 3);
//		setBackground( Color.gray );

                
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(700, 350));
		getContentPane().add(mainPanel);

		// Create a label and progress bar
		label1 = new JLabel("Waiting to start tasks...");
		label1.setPreferredSize(new Dimension(280, 30));
		mainPanel.add(label1);

		progress = new JProgressBar();
		progress.setPreferredSize(new Dimension(600, 30));
		progress.setMinimum(0);
		progress.setMaximum(200);
		progress.setValue(0);
		progress.setBounds(20, 35, 260, 20);
		mainPanel.add(progress);

		button = new JButton("Start");
		mainPanel.add(button);
		button.addActionListener(this);            
	}


    @Override
	public void actionPerformed( ActionEvent event )
	{
		if(event.getSource() == button)
		{
			// Prevent more button presses
			button.setEnabled( false );

			// Perform all of our bogus tasks
			for( int iCtr = 1; iCtr < 201; iCtr++ )
			{
				// Do some sort of simulated task
//				DoBogusTask( iCtr );

				// Update the progress indicator and label
				label1.setText( "Performing task " + iCtr + " of 200" );
				Rectangle labelRect = label1.getBounds();
				labelRect.x = 0;
				labelRect.y = 0;
				label1.paintImmediately( labelRect );

				progress.setValue( iCtr );
				Rectangle progressRect = progress.getBounds();
				progressRect.x = 0;
				progressRect.y = 0;
				progress.paintImmediately( progressRect );
			}
 		}
                this.dispose();
                
	}

//	public void DoBogusTask( int iCtr )
//	{
//		Random random = new Random( iCtr );
//
//		// Waste some time
//		for( int iValue = 0; iValue < random.nextFloat() * 100; iValue++ )
//		{
////			System.out.println( "iValue=" + iValue );
//		}
//	}

	public void prog()
	{
		// Create an instance of the test application
		ProgressBar mainFrame	= new ProgressBar();
                
		mainFrame.setVisible( true );
		mainFrame.pack();
                
	}
}