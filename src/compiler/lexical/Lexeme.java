/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.lexical;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daldegam
 */
public class Lexeme {

    private int type;
    private String lexeme;
    private int sourceLine;
    private int sourceColumn;
    
    private VariableClass variableClass;
    private VariableType variableType;

    public Lexeme() {
        this.lexeme = "";
        this.variableClass = VariableClass.NULL;
        this.variableType = VariableType.NULL;
    }
    
    public Lexeme(String lexeme, int type) {
        this.lexeme = lexeme;
        this.type = type;
        this.variableClass = VariableClass.NULL;
        this.variableType = VariableType.NULL;
    }

    public Lexeme setType(int type) {
        this.type = type;
        return this;
    }

    public int getType() {
        return this.type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public void appendLexeme(char character) {
        this.lexeme += character;
    }

    public Lexeme removeLastChar() {
        this.lexeme = this.lexeme.substring(0, this.lexeme.length() - 1);
        return this;
    }

    public String getTypeString() {
        return LexemeType.getTypeName(this.type);
    }
    
    public int getSourceLine() {
        return sourceLine;
    }

    public void setSourceLine(int sourceLine) {
        this.sourceLine = sourceLine;
    }

    public int getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(int sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public VariableClass getVariableClass() {
        return variableClass;
    }

    public void setVariableClass(VariableClass variableClass) {
        this.variableClass = variableClass;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }
}
