/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.lexical;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daldegam
 */
public class Lexical {

    /**
     * Lista de erros
     */
    private List<String> errors;

    public List<String> getErrors() {
        return this.errors;
    }

    private List<String> output;

    public List<String> getOutput() {
        return this.output;
    }

    /**
     * Código fonte
     */
    private String sourceCode;

    /**
     * Posição da leitura no código fonte
     */
    private int sourceOffsetPointer;

    /**
     * Estado do automato finito
     */
    private int finiteState = 0;

    /**
     * Construtor
     *
     * @param sourceCode Codigo fonte
     */
    public Lexical(String sourceCode) {
        this.sourceCode = sourceCode;
        this.sourceCode += '\0';
        this.sourceOffsetPointer = 0;
        this.errors = new ArrayList<String>();
        this.output = new ArrayList<String>();
    }
    
    private void addErrorCommentWithoutEnd() {
        this.errors.add("Comment without end (fatal error)\n");
    }
    
    private void addErrorStringWithoutEnd() {
        this.errors.add("String without end (fatal error)\n");
    }

    private void addErrorUnknownSymbol(char c) {
        this.errors.add("Unknown symbol: " + c + "\n");
    }

    private void addErrorUnexpectedSymbol(char c) {
        this.errors.add("Unexpected symbol: " + c + "\n");
    }

    private void addOutput(String message) {
        this.output.add(message);
    }

    /**
     * Inicializa o analisador lexico
     */
    public void parser() {
        Lexeme lexeme = this.getToken();
        while (lexeme != null) {

            this.addOutput(String.format("<%s,\"%s\">\n", lexeme.getTypeString(), lexeme.getLexeme()));

            lexeme = this.getToken(); // get next token
        }
    }

    /**
     * Obtem o proximo char do codigo fonte
     *
     * @return caractere a ser tratado
     */
    private char getNextChar() {
        return this.sourceCode.charAt(this.sourceOffsetPointer++);
    }

    private Lexeme getToken() {
        this.finiteState = 0;
        Lexeme lexeme = new Lexeme();
        while (this.sourceOffsetPointer < this.sourceCode.length()) {
            char currentChar = this.getNextChar();
            lexeme.appendLexeme(currentChar);
            //System.out.print(currentChar);

            switch (this.finiteState) {
                case 0:
                    if (currentChar == '\0') {
                        return lexeme.removeLastChar().setType(LexemeType.END_OF_FILE);
                    }
                    if (currentChar == ' ' || currentChar == '\n' || currentChar == '\t') {
                        lexeme.removeLastChar();
                        this.finiteState = 0;
                    } else if (currentChar == '0') {
                        this.finiteState = LexemeType.NUM_DEC;
                    } else if (currentChar >= '1' && currentChar <= '9') {
                        this.finiteState = LexemeType.NUM_DEC + 1;
                    } else if ((currentChar >= 'a' && currentChar <= 'z') || currentChar >= 'A' && currentChar <= 'Z') {
                        this.finiteState = LexemeType.IDENTIFIER;
                    } else if (currentChar == ';') {
                        return lexeme.setType(LexemeType.TERMINATOR);
                    } else if (currentChar == ',') {
                        return lexeme.setType(LexemeType.COMMA);
                    } else if (currentChar == '{') {
                        return lexeme.setType(LexemeType.KEY_OPEN);
                    } else if (currentChar == '}') {
                        return lexeme.setType(LexemeType.KEY_CLOSE);
                    } else if (currentChar == '[') {
                        return lexeme.setType(LexemeType.BRACKETS_OPEN);
                    } else if (currentChar == ']') {
                        return lexeme.setType(LexemeType.BRACKETS_CLOSE);
                    } else if (currentChar == '(') {
                        return lexeme.setType(LexemeType.PARENTHESIS_OPEN);
                    } else if (currentChar == ')') {
                        return lexeme.setType(LexemeType.PARENTHESIS_CLOSE);
                    } else if (currentChar == '+') {
                        return lexeme.setType(LexemeType.OP_ARITMETIC_PLUS);
                    } else if (currentChar == '-') {
                        return lexeme.setType(LexemeType.OP_ARITMETIC_LESS);
                    } else if (currentChar == '*') {
                        return lexeme.setType(LexemeType.OP_ARITMETIC_MULT);
                    } else if (currentChar == '%') {
                        return lexeme.setType(LexemeType.OP_ARITMETIC_MOD);
                    } else if (currentChar == '/') {
                        this.finiteState = LexemeType.OP_ARITMETIC_DIV;
                    } else if (currentChar == '|') {
                        this.finiteState = LexemeType.OP_LOGICAL_OR;
                    } else if (currentChar == '&') {
                        this.finiteState = LexemeType.OP_LOGICAL_AND;
                    } else if (currentChar == '!') {
                        return lexeme.setType(LexemeType.OP_LOGICAL_NOT);
                    } else if (currentChar == '<') {
                        this.finiteState = LexemeType.OP_REL_LT;
                    } else if (currentChar == '=') {
                        this.finiteState = LexemeType.OP_ATTRIBUTION;
                    } else if (currentChar == '>') {
                        this.finiteState = LexemeType.OP_REL_GT;
                    } else if (currentChar == '"') {
                        this.finiteState = LexemeType.STRING;
                    } else {
                        lexeme.removeLastChar();
                        this.addErrorUnknownSymbol(currentChar);
                    }
                    break;
                case LexemeType.NUM_DEC:
                    if (currentChar >= '0' && currentChar <= '7') {
                        this.finiteState = LexemeType.NUM_OCT;
                    } else if (currentChar == '.') {
                        this.finiteState = LexemeType.NUM_REAL;
                    } else if (currentChar == 'X' || currentChar == 'x') {
                        this.finiteState = LexemeType.NUM_HEX;
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.NUM_DEC);
                    }
                    break;
                case LexemeType.NUM_DEC + 1:
                    if (currentChar >= '0' && currentChar <= '9') {
                        this.finiteState = LexemeType.NUM_DEC + 1; // do not change state
                    } else if (currentChar == '.') {
                        this.finiteState = LexemeType.NUM_REAL;
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.NUM_DEC);
                    }
                    break;
                case LexemeType.NUM_OCT:
                    if (currentChar >= '0' && currentChar <= '7') {
                        this.finiteState = LexemeType.NUM_OCT; // do not change state
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.NUM_OCT);
                    }
                    break;
                case LexemeType.NUM_REAL:
                    if (currentChar >= '0' && currentChar <= '9') {
                        this.finiteState = LexemeType.NUM_REAL; // do not change state
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.NUM_REAL);
                    }
                    break;
                case LexemeType.NUM_HEX:
                    if (currentChar >= '0' && currentChar <= '9') {
                        this.finiteState = LexemeType.NUM_HEX; // do not change state
                    } else if ((currentChar >= 'A' && currentChar <= 'F') || (currentChar >= 'a' && currentChar <= 'f')) {
                        this.finiteState = LexemeType.NUM_HEX; // do not change state
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.NUM_HEX);
                    }
                    break;
                case LexemeType.IDENTIFIER:
                    if ((currentChar >= 'a' && currentChar <= 'z') || currentChar >= 'A' && currentChar <= 'Z') {
                        this.finiteState = LexemeType.IDENTIFIER;
                    } else if (currentChar >= '0' && currentChar <= '9') {
                        this.finiteState = LexemeType.IDENTIFIER;
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.IDENTIFIER);
                    }
                    break;
                case LexemeType.OP_LOGICAL_OR:
                    if (currentChar == '|') {
                        return lexeme.setType(LexemeType.OP_LOGICAL_OR);
                    } else {
                        lexeme.removeLastChar();
                        this.addErrorUnexpectedSymbol(currentChar);
                    }
                    break;
                case LexemeType.OP_LOGICAL_AND:
                    if (currentChar == '&') {
                        return lexeme.setType(LexemeType.OP_LOGICAL_AND);
                    } else {
                        lexeme.removeLastChar();
                        this.addErrorUnexpectedSymbol(currentChar);
                    }
                    break;
                case LexemeType.OP_REL_LT:
                    if (currentChar == '=') {
                        return lexeme.setType(LexemeType.OP_REL_LE);
                    } else if (currentChar == '>') {
                        return lexeme.setType(LexemeType.OP_REL_NE);
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.OP_REL_LT);
                    }
                case LexemeType.OP_ATTRIBUTION:
                    if (currentChar == '=') {
                        return lexeme.setType(LexemeType.OP_REL_EQ);
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.OP_ATTRIBUTION);
                    }
                case LexemeType.OP_REL_GT:
                    if (currentChar == '=') {
                        return lexeme.setType(LexemeType.OP_REL_GE);
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.removeLastChar().setType(LexemeType.OP_REL_GT);
                    }
                case LexemeType.OP_ARITMETIC_DIV: // possible comment
                    lexeme.removeLastChar();
                    if (currentChar == '*') {
                        lexeme.removeLastChar(); 
                        this.finiteState = LexemeType.OP_ARITMETIC_DIV + 1; // comment block
                    } else if (currentChar == '/') {
                        lexeme.removeLastChar(); 
                        this.finiteState = LexemeType.OP_ARITMETIC_DIV + 3; // comment line
                    } else {
                        this.sourceOffsetPointer--;
                        return lexeme.setType(LexemeType.OP_ARITMETIC_DIV); // operator div
                    }
                    break;
                case LexemeType.OP_ARITMETIC_DIV + 1: // comment block
                    lexeme.removeLastChar();
                    if (currentChar == '*') {
                        this.finiteState = LexemeType.OP_ARITMETIC_DIV + 2;
                    }
                    else if(currentChar == '\0') {
                        this.addErrorCommentWithoutEnd();
                    }
                    break;
                case LexemeType.OP_ARITMETIC_DIV + 2: // comment block
                    lexeme.removeLastChar();
                    if (currentChar == '/') {
                        this.finiteState = 0; // begin to initial state
                    } else {
                        this.finiteState = LexemeType.OP_ARITMETIC_DIV + 1;
                    }
                    break;
                case LexemeType.OP_ARITMETIC_DIV + 3: // comment
                    lexeme.removeLastChar();
                    if (currentChar == '\n') {
                        this.finiteState = 0; // begin to initial state
                    }
                    break;
                case LexemeType.STRING:
                    if(currentChar == '\"') {
                        return lexeme.setType(LexemeType.STRING);
                    }
                    else if (currentChar == '\\') {
                        this.finiteState = LexemeType.STRING + 1;
                    }
                    else if (currentChar == '\0') {
                        this.addErrorStringWithoutEnd();
                    }
                    break;
                case LexemeType.STRING + 1:
                    this.finiteState = LexemeType.STRING; // just consume char and back to string state
                    break;
                default:
                    this.addErrorUnknownSymbol(currentChar);
            }
        }
        return null;
    }
}
