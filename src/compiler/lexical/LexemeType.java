/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.lexical;

/**
 *
 * @author Daldegam
 */
public class LexemeType {
    public static final int END_OF_FILE             = 0xFFFFFFFF;
    public static final int NUM_DEC                 = 0x00000100;
    public static final int NUM_HEX                 = 0x00000200;
    public static final int NUM_REAL                = 0x00000300;
    public static final int NUM_OCT                 = 0x00000400;
    public static final int STRING                  = 0x00000500;
    public static final int COMMA                   = 0x00000600;
    public static final int OP_REL_LT               = 0x00000700;
    public static final int OP_REL_LE               = 0x00000800;
    public static final int OP_REL_NE               = 0x00000900;
    public static final int OP_REL_GT               = 0x00000A00;
    public static final int OP_REL_GE               = 0x00000B00;
    public static final int OP_REL_EQ               = 0x00000C00;
    public static final int OP_LOGICAL_AND          = 0x00000D00;
    public static final int OP_LOGICAL_OR           = 0x00000E00;
    public static final int OP_LOGICAL_NOT          = 0x00000F00;
    public static final int OP_ARITMETIC_PLUS       = 0x00001000;
    public static final int OP_ARITMETIC_LESS       = 0x00001200;
    public static final int OP_ARITMETIC_MULT       = 0x00001300;
    public static final int OP_ARITMETIC_DIV        = 0x00001400;
    public static final int OP_ARITMETIC_MOD        = 0x00001500;
    public static final int OP_ATTRIBUTION          = 0x00001600;
    public static final int TERMINATOR              = 0x00001700;
    public static final int KEY_OPEN                = 0x00001800;
    public static final int KEY_CLOSE               = 0x00001900;
    public static final int PARENTHESIS_OPEN        = 0x00001A00;
    public static final int PARENTHESIS_CLOSE       = 0x00001B00;
    public static final int BRACKETS_OPEN           = 0x00001C00;
    public static final int BRACKETS_CLOSE          = 0x00001D00;
    public static final int TRUE                    = 0x00001E00;
    public static final int FALSE                   = 0x00001F00;
    public static final int IF                      = 0x00002000;
    public static final int ELSE                    = 0x00002100;
    public static final int WHILE                   = 0x00002300;
    public static final int BREAK                   = 0x00002400;
    public static final int FOR                     = 0x00002500;
    public static final int TYPE_CHAR               = 0x00002600;
    public static final int TYPE_INT                = 0x00002700;
    public static final int TYPE_DOUBLE             = 0x00002800;
    public static final int TYPE_BOOL               = 0x00002900;
    public static final int IDENTIFIER              = 0x00002A00;
}
