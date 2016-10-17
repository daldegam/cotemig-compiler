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

    public Lexeme() {
        this.lexeme = "";
    }
    
    public Lexeme(String lexeme, int type) {
        this.lexeme = lexeme;
        this.type = type;
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
}
