/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metin.analizi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author echelon
 */
public class ui extends javax.swing.JFrame {
    
    FafEvents faf = new FafEvents();
    
    public ui() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        openfile = new javax.swing.JMenuItem();
        openfolder = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        help = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        file.setMnemonic('f');
        file.setText("File");

        openfile.setText("Open File");
        openfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openfileActionPerformed(evt);
            }
        });
        file.add(openfile);

        openfolder.setText("Open Folder");
        file.add(openfolder);

        exit.setMnemonic('x');
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        file.add(exit);

        menuBar.add(file);

        help.setMnemonic('h');
        help.setText("Help");

        about.setMnemonic('a');
        about.setText("About");
        help.add(about);

        menuBar.add(help);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void openfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openfileActionPerformed
        // TODO add your handling code here:
        String filePath;
        try {
            filePath = faf.chooseFile();
            System.out.println("Dosya seçildi!");
        } catch (IOException ex) {
            Logger.getLogger(ui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openfileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ui().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu file;
    private javax.swing.JMenu help;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openfile;
    private javax.swing.JMenuItem openfolder;
    // End of variables declaration//GEN-END:variables

}
