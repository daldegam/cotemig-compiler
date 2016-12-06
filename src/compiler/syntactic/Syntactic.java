/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntactic;

import compiler.SymbolTable;
import compiler.lexical.Lexeme;
import compiler.lexical.LexemeType;
import compiler.lexical.Lexical;
import compiler.lexical.VariableClass;
import compiler.lexical.VariableType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Daldegam
 */
public class Syntactic {

    /**
     * Lista de erros
     */
    private List<String> errors;

    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Levanta um erro de simbolo não esperado
     *
     * @param lexeme Lemexa recebido
     */
    private void raiseErrorUnexpectedSymbol(Lexeme lexeme) {
        this.errors.add("Unexpected symbol: " + lexeme.getLexeme() + ", line: "
                + (lexeme.getSourceLine() + 1) + ", position: " + lexeme.getSourceColumn() + "\n");
    }

    /**
     * Levanta um erro de simbolo não esperado
     *
     * @param lexeme Lexema recebido
     * @param expected Tipo esperado
     */
    private void raiseErrorUnexpectedSymbol(Lexeme lexeme, int expected) {
        this.errors.add("Unexpected symbol: " + lexeme.getLexeme() + ", line: "
                + (lexeme.getSourceLine() + 1) + ", position: " + lexeme.getSourceColumn()
                + ", Expected: " + LexemeType.getTypeName(expected) + "\n");
    }

    /**
     * Instância do analisador lexico
     */
    private Lexical lexical;

    /**
     * Tabela de simbolos
     */
    private SymbolTable symbolTable;

    /**
     * Construtor
     *
     * @param lexical
     * @param symbolTable
     */
    public Syntactic(Lexical lexical, SymbolTable symbolTable) {
        this.lexical = lexical;
        this.symbolTable = symbolTable;
        this.errors = new ArrayList<String>();
    }

    public void run() {

//        Test if all right
//        Lexeme lexeme = this.lexical.getToken();
//        while (lexeme != null) {
//            this.symbolTable.checkAndInstall(lexeme);
//            lexeme = this.lexical.getToken();
//        }
        this.lexeme = this.lexical.getToken();

        if (this.lexeme.getType() == LexemeType.END_OF_FILE) {
            JOptionPane.showMessageDialog(null,
                    "Source not detected!",
                    "Oops!",
                    JOptionPane.ERROR_MESSAGE);
        }

        try {
            // Start
            PROG();
        } catch (SourceErrorException ex) {
            System.out.println("Source getting end of file unexpected way... your code have a problem!");
        }
    }

    private Lexeme lexeme;

    private boolean matchToken(int expected) throws SourceErrorException {
        if (lexeme == null) {
            throw new SourceErrorException();
        } else if (lexeme.getType() == expected) {

            // if current lexeme is a Identifier, check and install into symboltable
            if (lexeme.getType() == LexemeType.IDENTIFIER) {
                this.symbolTable.checkAndInstall(lexeme);
            }

            // Get next lexeme
            this.lexeme = this.lexical.getToken();
            return true;
        } else {
            System.out.println(">> Unexpected token...");
            this.raiseErrorUnexpectedSymbol(lexeme, expected);
        }
        return false;
    }

    private void PROG() throws SourceErrorException {
        System.out.println("Called PROG();");

        while (this.lexeme != null
                && this.lexeme.getType() != LexemeType.END_OF_FILE) {
            this.CMD();
        }
    }

    private VariableType TYPE() throws SourceErrorException {
        System.out.println("Called TYPE();");
        switch (this.lexeme.getType()) {
            case LexemeType.TYPE_CHAR:
                this.matchToken(LexemeType.TYPE_CHAR);
                return VariableType.TYPE_CHAR;
            case LexemeType.TYPE_INT:
                this.matchToken(LexemeType.TYPE_INT);
                return VariableType.TYPE_INT;
            case LexemeType.TYPE_REAL:
                this.matchToken(LexemeType.TYPE_REAL);
                return VariableType.TYPE_REAL;
            case LexemeType.TYPE_BOOL:
                this.matchToken(LexemeType.TYPE_BOOL);
                return VariableType.TYPE_BOOL;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
        }
        return VariableType.NULL;
    }

    private VariableClass VAR(VariableType varType) throws SourceErrorException {
        System.out.println("Called VAR();");
        Lexeme variableReference = this.lexeme;
        this.matchToken(LexemeType.IDENTIFIER);
        
        if(varType != null) {
            variableReference.setVariableType(varType);
        }

        if (this.lexeme.getType() == LexemeType.BRACKETS_OPEN) { // Array

            this.matchToken(LexemeType.BRACKETS_OPEN);

            switch (this.lexeme.getType()) {
                case LexemeType.NUM_DEC:
                    this.matchToken(LexemeType.NUM_DEC);
                    break;
                case LexemeType.IDENTIFIER:
                    this.EXP();
                    break;
                default:
                    this.raiseErrorUnexpectedSymbol(lexeme);
                    break;
            }

            this.matchToken(LexemeType.BRACKETS_CLOSE);

            variableReference.setVariableClass(VariableClass.ARRAY);
            return VariableClass.ARRAY; // Array
        }

        variableReference.setVariableClass(VariableClass.VARIABLE);
        return VariableClass.VARIABLE; // Variable
    }

    private void DECLARATION() throws SourceErrorException {
        System.out.println("Called DECLARATION();");
        VariableType varType = this.TYPE();
        VariableClass type = this.VAR(varType);
        if (type == VariableClass.ARRAY) { // array
            this.DECLARATION_ARRAY();
        } else if (this.lexeme.getType() == LexemeType.OP_ATTRIBUTION) {
            this.DECLARATION_VAR();
        }
        this.matchToken(LexemeType.TERMINATOR);
    }

    private void DECLARATION_VAR() throws SourceErrorException {
        System.out.println("Called DECLARATION_VAR();");
        this.matchToken(LexemeType.OP_ATTRIBUTION);
        this.EXP();
    }

    private void DECLARATION_ARRAY() throws SourceErrorException {
        System.out.println("Called DECLARATION_ARRAY();");
        if (this.lexeme.getType() == LexemeType.OP_ATTRIBUTION) {
            this.matchToken(LexemeType.OP_ATTRIBUTION);
            this.matchToken(LexemeType.KEY_OPEN);

            if (this.lexeme.getType() == LexemeType.PARENTHESIS_OPEN
                    || this.lexeme.getType() == LexemeType.OP_LOGICAL_NOT
                    || this.lexeme.getType() == LexemeType.IDENTIFIER
                    || this.lexeme.getType() == LexemeType.NUM_DEC
                    || this.lexeme.getType() == LexemeType.NUM_HEX
                    || this.lexeme.getType() == LexemeType.NUM_REAL
                    || this.lexeme.getType() == LexemeType.NUM_OCT
                    || this.lexeme.getType() == LexemeType.TRUE
                    || this.lexeme.getType() == LexemeType.FALSE
                    || this.lexeme.getType() == LexemeType.STRING) {
                this.EXP();
            }

            while (this.lexeme.getType() == LexemeType.COMMA) {
                this.matchToken(LexemeType.COMMA);
                this.EXP();
            }

            this.matchToken(LexemeType.KEY_CLOSE);
        }
    }

    private void ATTRIBUTION() throws SourceErrorException {
        System.out.println("Called ATTRIBUTION();");
        this.VAR(null);
        this.matchToken(LexemeType.OP_ATTRIBUTION);
        this.EXP();
        this.matchToken(LexemeType.TERMINATOR);

    }

    private void IF() throws SourceErrorException {
        System.out.println("Called IF();");
        this.matchToken(LexemeType.IF);
        this.matchToken(LexemeType.PARENTHESIS_OPEN);
        this.EXP();
        this.matchToken(LexemeType.PARENTHESIS_CLOSE);

        this.CMD();

        if (this.lexeme.getType() == LexemeType.ELSE) {
            this.matchToken(LexemeType.ELSE);
            this.CMD();
        }
    }

    private void WHILE() throws SourceErrorException {
        System.out.println("Called WHILE();");
        this.matchToken(LexemeType.WHILE);
        this.matchToken(LexemeType.PARENTHESIS_OPEN);
        this.EXP();
        this.matchToken(LexemeType.PARENTHESIS_CLOSE);

        this.matchToken(LexemeType.KEY_OPEN);

        this.CMD();

        if (this.lexeme.getType() == LexemeType.BREAK) {
            this.matchToken(LexemeType.BREAK);
        }

        this.matchToken(LexemeType.KEY_CLOSE);
    }

    private void FOR() throws SourceErrorException {
        System.out.println("Called READ();");
        this.matchToken(LexemeType.FOR);
        this.matchToken(LexemeType.PARENTHESIS_OPEN);

        // warning! attribution has expected a terminator on end!
        if (this.lexeme.getType() == LexemeType.IDENTIFIER) {
            this.ATTRIBUTION();
        } else {
            this.matchToken(LexemeType.TERMINATOR);
        }

        if (this.lexeme.getType() == LexemeType.PARENTHESIS_OPEN
                || this.lexeme.getType() == LexemeType.OP_LOGICAL_NOT
                || this.lexeme.getType() == LexemeType.IDENTIFIER
                || this.lexeme.getType() == LexemeType.NUM_DEC
                || this.lexeme.getType() == LexemeType.NUM_HEX
                || this.lexeme.getType() == LexemeType.NUM_REAL
                || this.lexeme.getType() == LexemeType.NUM_OCT
                || this.lexeme.getType() == LexemeType.TRUE
                || this.lexeme.getType() == LexemeType.FALSE
                || this.lexeme.getType() == LexemeType.STRING) {
            this.EXP();
        }
        this.matchToken(LexemeType.TERMINATOR);

        if (this.lexeme.getType() == LexemeType.PARENTHESIS_OPEN
                || this.lexeme.getType() == LexemeType.OP_LOGICAL_NOT
                || this.lexeme.getType() == LexemeType.IDENTIFIER
                || this.lexeme.getType() == LexemeType.NUM_DEC
                || this.lexeme.getType() == LexemeType.NUM_HEX
                || this.lexeme.getType() == LexemeType.NUM_REAL
                || this.lexeme.getType() == LexemeType.NUM_OCT
                || this.lexeme.getType() == LexemeType.TRUE
                || this.lexeme.getType() == LexemeType.FALSE
                || this.lexeme.getType() == LexemeType.STRING) {
            this.EXP();
        }

        this.matchToken(LexemeType.PARENTHESIS_CLOSE);
        this.CMD();

    }

    private void CMD() throws SourceErrorException {
        System.out.println("Called CMD();");
        switch (this.lexeme.getType()) {
            case LexemeType.IF:
                this.IF();
                break;
            case LexemeType.WHILE:
                this.WHILE();
                break;
            case LexemeType.FOR:
                this.FOR();
                break;
            case LexemeType.PRINT:
                this.PRINT();
                break;
            case LexemeType.READ:
                this.READ();
                break;
            case LexemeType.TYPE_CHAR:
            case LexemeType.TYPE_INT:
            case LexemeType.TYPE_REAL:
            case LexemeType.TYPE_BOOL:
                this.DECLARATION();
                break;
            case LexemeType.IDENTIFIER:
                this.ATTRIBUTION();
                break;
            case LexemeType.KEY_OPEN:
                this.BLOCK();
                break;
            case LexemeType.END_OF_FILE:
                break;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
                this.lexeme = this.lexical.getToken(); // get next token
        }
    }

    private void BLOCK() throws SourceErrorException {
        System.out.println("Called BLOCK();");
        this.matchToken(LexemeType.KEY_OPEN);

        while (this.lexeme.getType() == LexemeType.IF
                || this.lexeme.getType() == LexemeType.WHILE
                || this.lexeme.getType() == LexemeType.FOR
                || this.lexeme.getType() == LexemeType.PRINT
                || this.lexeme.getType() == LexemeType.READ
                || this.lexeme.getType() == LexemeType.TYPE_CHAR
                || this.lexeme.getType() == LexemeType.TYPE_INT
                || this.lexeme.getType() == LexemeType.TYPE_REAL
                || this.lexeme.getType() == LexemeType.TYPE_BOOL
                || this.lexeme.getType() == LexemeType.IDENTIFIER
                || this.lexeme.getType() == LexemeType.KEY_OPEN) {
            this.CMD();
        }

        this.matchToken(LexemeType.KEY_CLOSE);
    }

    private void EXP() throws SourceErrorException {
        System.out.println("Called EXP();");
        this.EXPS();

        if (this.lexeme.getType() == LexemeType.OP_REL_EQ
                || this.lexeme.getType() == LexemeType.OP_REL_GE
                || this.lexeme.getType() == LexemeType.OP_REL_GT
                || this.lexeme.getType() == LexemeType.OP_REL_LE
                || this.lexeme.getType() == LexemeType.OP_REL_LT
                || this.lexeme.getType() == LexemeType.OP_REL_NE) {
            this.OP_REL();
            this.EXPS();
        }
    }

    private void EXPS() throws SourceErrorException {
        System.out.println("Called EXPS();");
        this.TERM();

        while (this.lexeme.getType() == LexemeType.OP_ARITMETIC_PLUS
                || this.lexeme.getType() == LexemeType.OP_ARITMETIC_LESS
                || this.lexeme.getType() == LexemeType.OP_ARITMETIC_INC
                || this.lexeme.getType() == LexemeType.OP_ARITMETIC_DEC
                || this.lexeme.getType() == LexemeType.OP_LOGICAL_OR) {

            if (this.lexeme.getType() == LexemeType.OP_ARITMETIC_INC
                    || this.lexeme.getType() == LexemeType.OP_ARITMETIC_DEC) {
                this.OP_ADD(); // if ++ or -- not call TERM
            } else {
                this.OP_ADD();
                this.TERM();
            }
        }
    }

    private void TERM() throws SourceErrorException {
        System.out.println("Called TERM();");
        this.FACTOR();
        while (this.lexeme.getType() == LexemeType.OP_ARITMETIC_MULT
                || this.lexeme.getType() == LexemeType.OP_ARITMETIC_DIV
                || this.lexeme.getType() == LexemeType.OP_ARITMETIC_MOD
                || this.lexeme.getType() == LexemeType.OP_LOGICAL_AND
                || this.lexeme.getType() == LexemeType.OP_LOGICAL_NOT) {
            this.OP_MUL();
            this.FACTOR();
        }
    }

    private void FACTOR() throws SourceErrorException {
        System.out.println("Called FACTOR();");
        switch (this.lexeme.getType()) {
            case LexemeType.PARENTHESIS_OPEN:
                this.matchToken(LexemeType.PARENTHESIS_OPEN);
                this.EXP();
                this.matchToken(LexemeType.PARENTHESIS_CLOSE);
                break;
            case LexemeType.OP_LOGICAL_NOT:
                this.matchToken(LexemeType.OP_LOGICAL_NOT);
                this.FACTOR();
                break;
            case LexemeType.IDENTIFIER:
                this.VAR(null);
                break;
            case LexemeType.NUM_DEC:
                this.matchToken(LexemeType.NUM_DEC);
                break;
            case LexemeType.NUM_HEX:
                this.matchToken(LexemeType.NUM_HEX);
                break;
            case LexemeType.NUM_REAL:
                this.matchToken(LexemeType.NUM_REAL);
                break;
            case LexemeType.NUM_OCT:
                this.matchToken(LexemeType.NUM_OCT);
                break;
            case LexemeType.TRUE:
                this.matchToken(LexemeType.TRUE);
                break;
            case LexemeType.FALSE:
                this.matchToken(LexemeType.FALSE);
                break;
            case LexemeType.STRING:
                this.matchToken(LexemeType.STRING);
                break;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
        }
    }

    private void OP_REL() throws SourceErrorException {
        System.out.println("Called OP_REL();");
        switch (this.lexeme.getType()) {
            case LexemeType.OP_REL_EQ:
                matchToken(LexemeType.OP_REL_EQ);
                break;
            case LexemeType.OP_REL_GE:
                matchToken(LexemeType.OP_REL_GE);
                break;
            case LexemeType.OP_REL_GT:
                matchToken(LexemeType.OP_REL_GT);
                break;
            case LexemeType.OP_REL_LE:
                matchToken(LexemeType.OP_REL_LE);
                break;
            case LexemeType.OP_REL_LT:
                matchToken(LexemeType.OP_REL_LT);
                break;
            case LexemeType.OP_REL_NE:
                matchToken(LexemeType.OP_REL_NE);
                break;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
        }
    }

    private void OP_ADD() throws SourceErrorException {
        System.out.println("Called OP_ADD();");
        switch (this.lexeme.getType()) {
            case LexemeType.OP_ARITMETIC_PLUS:
                matchToken(LexemeType.OP_ARITMETIC_PLUS);
                break;
            case LexemeType.OP_ARITMETIC_LESS:
                matchToken(LexemeType.OP_ARITMETIC_LESS);
                break;
            case LexemeType.OP_ARITMETIC_INC:
                matchToken(LexemeType.OP_ARITMETIC_INC);
                break;
            case LexemeType.OP_ARITMETIC_DEC:
                matchToken(LexemeType.OP_ARITMETIC_DEC);
                break;
            case LexemeType.OP_LOGICAL_OR:
                matchToken(LexemeType.OP_LOGICAL_OR);
                break;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
        }
    }

    private void OP_MUL() throws SourceErrorException {
        System.out.println("Called OP_MUL();");
        switch (this.lexeme.getType()) {
            case LexemeType.OP_ARITMETIC_MULT:
                matchToken(LexemeType.OP_ARITMETIC_MULT);
                break;
            case LexemeType.OP_ARITMETIC_DIV:
                matchToken(LexemeType.OP_ARITMETIC_DIV);
                break;
            case LexemeType.OP_ARITMETIC_MOD:
                matchToken(LexemeType.OP_ARITMETIC_MOD);
                break;
            case LexemeType.OP_LOGICAL_AND:
                matchToken(LexemeType.OP_LOGICAL_AND);
                break;
            case LexemeType.OP_LOGICAL_NOT:
                matchToken(LexemeType.OP_LOGICAL_NOT);
                break;
            default:
                this.raiseErrorUnexpectedSymbol(lexeme);
        }
    }

    private void PRINT() throws SourceErrorException {
        System.out.println("Called PRINT();");
        this.matchToken(LexemeType.PRINT);
        this.matchToken(LexemeType.PARENTHESIS_OPEN);
        this.matchToken(LexemeType.STRING);

        while (this.lexeme.getType() == LexemeType.COMMA) {
            this.matchToken(LexemeType.COMMA);
            this.FACTOR();
        }

        this.matchToken(LexemeType.PARENTHESIS_CLOSE);
        this.matchToken(LexemeType.TERMINATOR);
    }

    private void READ() throws SourceErrorException {
        System.out.println("Called READ();");
        this.matchToken(LexemeType.READ);
        this.matchToken(LexemeType.PARENTHESIS_OPEN);
        this.matchToken(LexemeType.STRING);
        while (this.lexeme.getType() == LexemeType.COMMA) {
            this.matchToken(LexemeType.COMMA);
            this.FACTOR();
        }

        this.matchToken(LexemeType.PARENTHESIS_CLOSE);
        this.matchToken(LexemeType.TERMINATOR);
    }

}
