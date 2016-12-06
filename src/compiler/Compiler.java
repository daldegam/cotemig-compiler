/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import compiler.lexical.Lexeme;
import javax.swing.JEditorPane;

import compiler.lexical.Lexical;
import compiler.syntactic.Syntactic;
import java.util.List;

/**
 *
 * @author Daldegam
 */
public class Compiler {

    private JEditorPane sourceEditor;

    private SymbolTable symbolTable;

    private Lexical lexical;
    
    private Syntactic syntactic;

    public List<String> getLexicalErrors() {
        return this.lexical.getErrors();
    }

    public List<String> getLexicalOutput() {
        return this.lexical.getOutput();
    }
    
    public List<String> getSymbolTable() {
        return this.symbolTable.getTableString();
    }
    
    public List<String> getSyntacticErrors() {
        return this.syntactic.getErrors();
    }

    public Compiler(JEditorPane sourceEditor) {
        this.sourceEditor = sourceEditor;
        this.symbolTable = new SymbolTable();
    }

    public void run() {
        this.lexical = new Lexical(this.symbolTable, this.sourceEditor.getText());
        this.syntactic = new Syntactic(this.lexical, this.symbolTable);
        
        this.syntactic.run();
    }
}
