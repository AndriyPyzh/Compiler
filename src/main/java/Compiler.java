import sun.font.DelegatingShape;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Compiler {
    private int index;
    private int temp_count;
    private LinkedList<Token> tokens;
    private LinkedList<Command> COMMANDS = new LinkedList<>();


    public Compiler(LinkedList<Token> tokens) {
        index = 0;
        this.tokens = tokens;
        temp_count = 1;
    }

    private Token token(int indx) {
        return tokens.get(indx);
    }

    public void compile() {

    }


    private void HandleBlock() {
        while (index < tokens.size() && !token(index).getLexeme().equals("}")) {
            if (token(index).isOperatorBlock()) {
                HandleOperatorBlock();
            } else {
                HandleCommand();
            }
            index++;
        }
    }

    private void HandleOperatorBlock() {
        boolean isloop = token(index).isLoop();
        index += 2;
        int goto_index = COMMANDS.size();
        String tempvar;
        if (token(index + 1).getLexeme().equals("]")) {
            tempvar = token(index).getLexeme();
            index += 2;
        } else {
            tempvar = String.format("t%d", temp_count);
            temp_count++;
            HandleExpressionArg(tempvar);
        }
        index++;
        COMMANDS.add(new Command("GOTOIFNOT", tempvar, "$"));
        int gotoifnot_index = COMMANDS.size() - 1;
        HandleBlock();
        if (isloop) {
            COMMANDS.add(new Command("GOTO", Integer.toString(goto_index)));
        }
        COMMANDS.get(gotoifnot_index).setArg2(Integer.toString(COMMANDS.size()));
        index++;
    }

    private void HandleExpressionArg(String var) {
        HandleExpression(var);
    }

    private void HandleExpressionArg() {
        String var = "";
        HandleExpression(var);
    }

    private void HandleCommand() {
        if (token(index).isIO()) {
            COMMANDS.add(new Command(token(index).getLexeme().toUpperCase(), token(index += 2).getLexeme()));
        } else {
            if (token(index + 3).getLexeme().equals(";")) {
                COMMANDS.add(new Command(token(index).getLexeme(), token(index += 2).getLexeme()));
            } else {
                HandleExpressionArg(token(index).getLexeme());
            }
        }
        index += 2;
    }

    private int priority(String operation) {
        if ("*/".contains(operation)) {
            return 1;
        } else if ("+-".contains(operation)) {
            return 2;
        }
        return 3;
    }

    private void generateCommand(){

    }

    private void HandleExpression(String var) {

        Stack<String> ARGUMENTS = new Stack<>();
        while (!token(index).getLexeme().equals("]") && !token(index).getLexeme().equals("}") && !token(index).getLexeme().equals(";")) {
            if (token(index).isVariable()) {
                ARGUMENTS.push(token(index).getLexeme());
            } else if (token(index).isOperator()) {
                while ()
            }
        }
    }

    public void WriteToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        COMMANDS.forEach((p) -> printWriter.println(p.toString()));
        printWriter.close();
    }


}
