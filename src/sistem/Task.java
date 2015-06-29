/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem;

import java.awt.Toolkit;
import java.util.Random;
import javax.swing.SwingWorker;

/**
 *
 * @author moehandi
 */
class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(1000);
                while (progress < 100 && !isCancelled()) {
                    //Sleep for up to one second.
                    Thread.sleep(random.nextInt(1000));
                    //Make random progress.
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
            } catch (InterruptedException ignore) {}
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
//            startButton.setEnabled(true);
//            progressMonitor.setProgress(0);
        }
    }