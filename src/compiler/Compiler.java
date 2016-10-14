/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import javax.swing.JEditorPane;

import compiler.lexical.Lexical;
import java.util.List;

/**
 *
 * @author Daldegam
 */
public class Compiler {

    private JEditorPane sourceEditor;

    private Lexical lexical;
    
    public List<String> getLexicalErrors() {
        return this.lexical.getErrors();
    }
    
    public List<String> getLexicalOutput() {
        return this.lexical.getOutput();
    }

    public Compiler(JEditorPane sourceEditor) {
        this.sourceEditor = sourceEditor;
    }

    public void run() {
        this.lexical = new Lexical(this.sourceEditor.getText());
        this.lexical.parser();
    }
}
