/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import compiler.Compiler;
import java.util.List;

/**
 *
 * @author Daldegam
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        // TODO: Deletar
        jEditorPaneCode.setText(
            "123123 0777 123.123123 0x123123 , leANdro Leandro teste123; " + 
            "{ asd } [ teste ] ( teste ) + - * % / if ( 11 && 22 || 33 == !44) { print }\n\n" +
            "1 < 2 && 2 <= 3 && 3 <> 4 || 2 == 5 || t = 6 || t > r || t >= 4 ^$ /* isso tem que ser ignorado */ \n " + 
            "//isso tambem deve ser ignorado \n" + 
            " teste = 123; \"string\" \"teste de string ok\" \"teste de string \\\" com aspas\" \"ts \\ ok\"  "
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPaneCode = new javax.swing.JEditorPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaErrors = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaLexicalOutput = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaSymbolTable = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuNew = new javax.swing.JMenuItem();
        jMenuOpen = new javax.swing.JMenuItem();
        jMenuClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuCompile = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jEditorPaneCode.setFont(new java.awt.Font("Courier", 0, 13)); // NOI18N
        jScrollPane1.setViewportView(jEditorPaneCode);

        jTextAreaErrors.setColumns(20);
        jTextAreaErrors.setFont(new java.awt.Font("Courier", 0, 13)); // NOI18N
        jTextAreaErrors.setRows(5);
        jScrollPane2.setViewportView(jTextAreaErrors);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Erros", jPanel1);

        jTextAreaLexicalOutput.setColumns(20);
        jTextAreaLexicalOutput.setFont(new java.awt.Font("Courier", 0, 13)); // NOI18N
        jTextAreaLexicalOutput.setRows(5);
        jScrollPane3.setViewportView(jTextAreaLexicalOutput);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Analise Léxica", jPanel2);

        jTextAreaSymbolTable.setColumns(20);
        jTextAreaSymbolTable.setRows(5);
        jScrollPane4.setViewportView(jTextAreaSymbolTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Tabela de simbolos", jPanel3);

        jMenu1.setText("Arquivo");

        jMenuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuNew.setText("Novo");
        jMenuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNewActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuNew);

        jMenuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuOpen.setText("Abrir");
        jMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuOpen);

        jMenuClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuClose.setText("Fechar");
        jMenuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCloseActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Debug");

        jMenuCompile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuCompile.setText("Compilar");
        jMenuCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCompileActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuCompile);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ajuda");

        jMenuAbout.setText("Sobre");
        jMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuAbout);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuCloseActionPerformed

    private void jMenuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                jEditorPaneCode.setContentType("text/plain");
                jEditorPaneCode.read(new BufferedReader(new FileReader(fc.getSelectedFile().getAbsoluteFile())), "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo.\n" + ex);
            }
        }
    }//GEN-LAST:event_jMenuOpenActionPerformed

    private void jMenuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNewActionPerformed
        jEditorPaneCode.setText("");
    }//GEN-LAST:event_jMenuNewActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutActionPerformed
        JOptionPane.showMessageDialog(this, "Trabalho de compiladores\n\nDesenvolvido por:\n\tLeandro Henrique Daldegam Fontes\n\tPaulo Henrique de Almeida\n\nFaculdade COTEMIG - 2016/2");
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void jMenuCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCompileActionPerformed
        jTextAreaErrors.setText("");
        jTextAreaLexicalOutput.setText("");
        jTextAreaSymbolTable.setText("");
        Compiler compiler = new Compiler(jEditorPaneCode);
        compiler.run();
        
        List<String> lexicalErrors = compiler.getLexicalErrors();
        for(String error : lexicalErrors) {
            jTextAreaErrors.append("Lexical Error: " + error);
        }
        
        List<String> lexicalOutputs = compiler.getLexicalOutput();
        for(String message : lexicalOutputs) {
            jTextAreaLexicalOutput.append(message);
        }
        
        List<String> symbolTableOutput = compiler.getSymbolTable();
        for(String message : symbolTableOutput) {
            jTextAreaSymbolTable.append(message);
        }
        
        List<String> syntacticErrors = compiler.getSyntacticErrors();
        for(String error : syntacticErrors) {
            jTextAreaErrors.append("Syntactic Error: " + error);
        }
        
        if(lexicalErrors.size() == 0 && syntacticErrors.size() == 0) {
            JOptionPane.showMessageDialog(this, "Compilado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuCompileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPaneCode;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuClose;
    private javax.swing.JMenuItem jMenuCompile;
    private javax.swing.JMenuItem jMenuNew;
    private javax.swing.JMenuItem jMenuOpen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaErrors;
    private javax.swing.JTextArea jTextAreaLexicalOutput;
    private javax.swing.JTextArea jTextAreaSymbolTable;
    // End of variables declaration//GEN-END:variables
}
