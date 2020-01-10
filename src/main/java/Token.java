import java.util.Arrays;

public class Token {
    private String lexeme;

    public Token(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public boolean isIO() {
        return lexeme.equals("read") || lexeme.equals("write");
    }

    public boolean isOperatorBlock() {
        return lexeme.equals("if") || lexeme.equals("while");
    }
    public boolean isLoop(){
        return lexeme.equals("while");
    }
    public boolean isVariable(){
        return lexeme.chars().allMatch(Character::isLetterOrDigit);
    }
    public boolean isOperator(){
        return Arrays.asList(new String[]{"+", "-", "*", "/",">","<"}).contains(lexeme);
    }
}
