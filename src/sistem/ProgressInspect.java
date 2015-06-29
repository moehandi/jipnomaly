

package sistem;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

public class ProgressInspect extends JPanel implements ActionListener, PropertyChangeListener {

    private ProgressMonitor progressMonitor;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;

    

    public ProgressInspect() {
        super(new BorderLayout());

        startButton = new JButton("Start");
        startButton.setActionCommand("Mulai");
        startButton.addActionListener(this);

        taskOutput = new JTextArea(5, 8);
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);
//
        add(startButton, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        progressMonitor = new ProgressMonitor(ProgressInspect.this,
                                  "Loading......",
                                  "", 0, 500);
        progressMonitor.setProgress(0);
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
        startButton.setEnabled(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName()) ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            taskOutput.append(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                    taskOutput.append("Task canceled.\n");
                    
                } else {
                    taskOutput.append("Task completed.\n");
                    progressMonitor.close();
                }
                startButton.setEnabled(true);
            }
        }

    }

    
}
