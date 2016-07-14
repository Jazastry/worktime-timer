/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worktimer;

import worktimer.WorkTimer;
import worktimer.LogType;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author yasen.lazarov
 */
public class Display extends JFrame implements Runnable {
    
    final static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    static Point mouseDownCompCoords;

    private long startTime;
    private long pausedTime = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private Thread updater;    
//    minutes change 
    private Timer minTimer;
    private final long delay = 100;
    private final long timeout = 500;
    
    
    private final Runnable displayUpdater = new Runnable() {
        public void run() {
            displayElapsedTime(System.currentTimeMillis() - startTime);
        }       
    };
    
    private void displayElapsedTime(long elapsedTime) {
         timeDisplay.setText(WorkTimer.longToDisplayString(elapsedTime));
    }
    
    public void run() {
        try {
            while (isRunning) {
                SwingUtilities.invokeAndWait(displayUpdater);
                Thread.sleep(50);
            }
        } catch (java.lang.reflect.InvocationTargetException ite) {
            ite.printStackTrace(System.err);
            // Should never happen!
        } catch (InterruptedException ie) {
        }
        // Ignore and return!
    }
    
    public void startTimer() {
        startTime = WorkTimer.getStartTime();
        if (isPaused) {           
            long gapTime = pausedTime - WorkTimer.getStartTime();
             startTime =  System.currentTimeMillis() - (pausedTime - WorkTimer.getStartTime());
              isPaused = false;
              WorkTimer.writeLog(LogType.START.getName(), gapTime);
        }        
        isRunning = true;
        updater = new Thread((Runnable) this);
        updater.start();        
    }

    private void stopTimer() {
        long elapsed = System.currentTimeMillis() - startTime;
        isRunning = false;
        try {
            updater.join();
            // Wait for updater to finish
        } catch (InterruptedException ie) {
        }
        displayElapsedTime(elapsed);
        setVisible(false); 
           
        dispose(); 
        invalidate();
        WorkTimer.writeLog(LogType.STOP.getName(), elapsed);
    }
    
    private void pauseTimer() {        
        if (isRunning) {
            long elapsed =  System.currentTimeMillis() - startTime;
            pausedTime = System.currentTimeMillis();
            isRunning = false;
            isPaused = true;
            startStopBtn.setText("Start");            
            try {
                updater.join();
                // Wait for updater to finish
            } catch (InterruptedException ie) {
            }         
            
            displayElapsedTime(elapsed);
            WorkTimer.writeLog(LogType.PAUSE.getName(), elapsed);
        }        
    }
    
    private void changeTime(String action) {
        minTimer = new java.util.Timer();
        minTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                    if ("add".equals(action)) {
                        addMinute();
                    } else {
                        removeMinute();
                    }
            }
        }, timeout, delay);
    }
    
    private void addMinute() {
        startTime -= (60 * 1000);
    }
    
    private void removeMinute() {
        startTime += (60 * 1000);
    }

    /**
     * Creates new form StartupLoggerWindow
     */
    public Display() {
        initComponents();
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = 0;
        setLocation(x, y);
    }
    
    public void startGadget() {
        setVisible(true);
        startTimer();
        run();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startStopBtn = new javax.swing.JButton();
        timeDisplay = new javax.swing.JTextField();
        pauseBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        minutesPanel = new javax.swing.JPanel();
        plusMinBtn = new javax.swing.JButton();
        minusMinBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(java.awt.Color.black);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(503, 76));
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mouseDraggedHandler(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startStopBtn.setBackground(new java.awt.Color(51, 51, 51));
        startStopBtn.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        startStopBtn.setForeground(new java.awt.Color(51, 153, 0));
        startStopBtn.setText("Stop");
        startStopBtn.setActionCommand("startStopBtn");
        startStopBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        startStopBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startStopBtnMouseClicked(evt);
            }
        });
        startStopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopBtnActionPerformed(evt);
            }
        });
        getContentPane().add(startStopBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 93, 78));

        timeDisplay.setBackground(new java.awt.Color(51, 0, 51));
        timeDisplay.setFont(new java.awt.Font("Verdana", 1, 48)); // NOI18N
        timeDisplay.setForeground(new java.awt.Color(0, 153, 51));
        timeDisplay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeDisplay.setText("00:00:00");
        timeDisplay.setAutoscrolls(false);
        timeDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        timeDisplay.setFocusable(false);
        timeDisplay.setVerifyInputWhenFocusTarget(false);
        timeDisplay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                timeDisplayMouseDragged(evt);
            }
        });
        timeDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                timeDisplayMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                timeDisplayMouseReleased(evt);
            }
        });
        timeDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeDisplayActionPerformed(evt);
            }
        });
        timeDisplay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                timeDisplayPropertyChange(evt);
            }
        });
        getContentPane().add(timeDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 290, 78));

        pauseBtn.setBackground(new java.awt.Color(51, 51, 51));
        pauseBtn.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        pauseBtn.setForeground(new java.awt.Color(51, 153, 0));
        pauseBtn.setText("Pause");
        pauseBtn.setActionCommand("pauseBtn");
        pauseBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pauseBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        pauseBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseBtnMouseClicked(evt);
            }
        });
        getContentPane().add(pauseBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 93, 78));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 0, -1, -1));

        minutesPanel.setBackground(new java.awt.Color(0, 0, 0));

        plusMinBtn.setBackground(new java.awt.Color(51, 51, 51));
        plusMinBtn.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        plusMinBtn.setForeground(new java.awt.Color(51, 153, 0));
        plusMinBtn.setText("+");
        plusMinBtn.setActionCommand("plusMin");
        plusMinBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        plusMinBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plusMinBtnMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                plusMinBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                plusMinBtnMouseReleased(evt);
            }
        });

        minusMinBtn.setBackground(new java.awt.Color(51, 51, 51));
        minusMinBtn.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        minusMinBtn.setForeground(new java.awt.Color(51, 153, 0));
        minusMinBtn.setText("-");
        minusMinBtn.setActionCommand("minusMin");
        minusMinBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        minusMinBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minusMinBtnMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minusMinBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                minusMinBtnMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout minutesPanelLayout = new javax.swing.GroupLayout(minutesPanel);
        minutesPanel.setLayout(minutesPanelLayout);
        minutesPanelLayout.setHorizontalGroup(
            minutesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minutesPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(minutesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plusMinBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minusMinBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        minutesPanelLayout.setVerticalGroup(
            minutesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minutesPanelLayout.createSequentialGroup()
                .addComponent(plusMinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(minusMinBtn))
        );

        getContentPane().add(minutesPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startStopBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startStopBtnMouseClicked
        if (isRunning) {
            stopTimer(); 
            startStopBtn.setText("Start");
        } else {
            startTimer();
            startStopBtn.setText("Stop");
        }     
    }//GEN-LAST:event_startStopBtnMouseClicked

    private void timeDisplayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_timeDisplayPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_timeDisplayPropertyChange

    private void mouseDraggedHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseDraggedHandler
        Point currCoords = evt.getLocationOnScreen();
        setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }//GEN-LAST:event_mouseDraggedHandler

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        mouseDownCompCoords = evt.getPoint();
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
         mouseDownCompCoords = null;
    }//GEN-LAST:event_formMouseReleased

    private void timeDisplayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeDisplayMousePressed
        mouseDownCompCoords = evt.getPoint();
    }//GEN-LAST:event_timeDisplayMousePressed

    private void timeDisplayMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeDisplayMouseReleased
        mouseDownCompCoords = null;
    }//GEN-LAST:event_timeDisplayMouseReleased

    private void timeDisplayMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeDisplayMouseDragged
        Point currCoords = evt.getLocationOnScreen();
        setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }//GEN-LAST:event_timeDisplayMouseDragged

    private void pauseBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseBtnMouseClicked
        if (isRunning) {
            pauseTimer();
        }
    }//GEN-LAST:event_pauseBtnMouseClicked

    private void plusMinBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plusMinBtnMouseClicked
        addMinute();
    }//GEN-LAST:event_plusMinBtnMouseClicked

    private void minusMinBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minusMinBtnMouseClicked
         removeMinute();
    }//GEN-LAST:event_minusMinBtnMouseClicked

    private void plusMinBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plusMinBtnMousePressed
        changeTime("add");
//        minTimer = new java.util.Timer();
//        minTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                addMinute();
//            }
//        }, timeout, delay);
    }//GEN-LAST:event_plusMinBtnMousePressed

    private void plusMinBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plusMinBtnMouseReleased
        minTimer.cancel();
    }//GEN-LAST:event_plusMinBtnMouseReleased

    private void minusMinBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minusMinBtnMousePressed
        changeTime("remove");
    }//GEN-LAST:event_minusMinBtnMousePressed

    private void minusMinBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minusMinBtnMouseReleased
         minTimer.cancel();
    }//GEN-LAST:event_minusMinBtnMouseReleased

    private void timeDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeDisplayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeDisplayActionPerformed

    private void startStopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startStopBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton minusMinBtn;
    private javax.swing.JPanel minutesPanel;
    private javax.swing.JButton pauseBtn;
    private javax.swing.JButton plusMinBtn;
    private javax.swing.JButton startStopBtn;
    private javax.swing.JTextField timeDisplay;
    // End of variables declaration//GEN-END:variables
}
