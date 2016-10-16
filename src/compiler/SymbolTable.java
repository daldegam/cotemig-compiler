/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import compiler.lexical.Lexeme;
import compiler.lexical.LexemeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Daldegam
 */
public class SymbolTable {

    private HashSet<Lexeme> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashSet<Lexeme>();
        this.installDefaultTokens();
    }

    private void installDefaultTokens() {
        this.add(new Lexeme("true", LexemeType.TRUE));
        this.add(new Lexeme("false", LexemeType.FALSE));
        this.add(new Lexeme("if", LexemeType.IF));
        this.add(new Lexeme("else", LexemeType.ELSE));
        this.add(new Lexeme("while", LexemeType.WHILE));
        this.add(new Lexeme("break", LexemeType.BREAK));
        this.add(new Lexeme("for", LexemeType.FOR));
        this.add(new Lexeme("char", LexemeType.TYPE_CHAR));
        this.add(new Lexeme("int", LexemeType.TYPE_INT));
        this.add(new Lexeme("double", LexemeType.TYPE_DOUBLE));
        this.add(new Lexeme("bool", LexemeType.TYPE_BOOL));
    }

    public void add(Lexeme lexeme) {
        this.symbolTable.add(lexeme);
    }

    public void checkAndInstall(Lexeme lexeme) {
        if (lexeme.getType() == LexemeType.IDENTIFIER) {
            for (Lexeme symbol : this.symbolTable) {
                if (symbol.getLexeme().equals(lexeme.getLexeme())) {
                    return; // has exists into table
                }
            }
            this.add(lexeme);
        }
    }

    public List<String> getTableString() {
        List<String> output = new ArrayList<String>();
        for (Lexeme symbol : this.symbolTable) {
            output.add(String.format("<%s,\"%s\">\n", symbol.getTypeString(), symbol.getLexeme()));
        }
        return output;
    }
}
