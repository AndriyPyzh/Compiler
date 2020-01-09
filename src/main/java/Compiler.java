import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void compile() {
        HandleBlock();
    }

    public void WriteToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        COMMANDS.forEach((p) -> printWriter.println(p.toString()));
        printWriter.close();
    }

    private Token token(int indx) {
        return tokens.get(indx);
    }

    private void HandleBlock() {
        while (index < tokens.size() && !token(index).getLexeme().equals("}")) {
            if (token(index).isOperatorBlock()) {
                HandleOperatorBlock();
            } else {
                HandleCommand();
            }
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
            HandleExpression(tempvar);
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


    private void HandleCommand() {
        if (token(index).isIO()) {
            COMMANDS.add(new Command(token(index).getLexeme().toUpperCase(), token(index += 2).getLexeme()));
            index += 2;

        } else {
            if (token(index + 3).getLexeme().equals(";")) {
                COMMANDS.add(new Command("COPY", token(index).getLexeme(), token(index += 2).getLexeme()));
                index += 2;
            } else {
                index += 2;
                HandleExpression(token(index - 2).getLexeme());
            }
        }
    }

    private int priority(String operation) {
        if ("*/".contains(operation)) {
            return 1;
        } else if ("+-".contains(operation)) {
            return 2;
        }
        return 3;
    }

    private void generateCommand(Stack<String> OPERATIONS, Stack<String> ARGUMENTS) {
        String op = OPERATIONS.pop();
        String arg1 = ARGUMENTS.pop();
        String arg2 = ARGUMENTS.pop();
        String command = "";
        switch (op) {
            case "+":
                command = "ADD";
                break;
            case "-":
                command = "SUB";
                break;
            case "*":
                command = "MUL";
                break;
            case "/":
                command = "DIV";
                break;
            default:
                System.err.println("Invalid Operation");
                System.exit(1);
        }

        String res_var = "t" + temp_count++;
        COMMANDS.add(new Command(command, arg2, arg1, res_var));
        ARGUMENTS.push(res_var);

    }

    private void HandleExpression(String... args) {
        Stack<String> ARGUMENTS = new Stack<>();
        Stack<String> OPERATIONS = new Stack<>();
        while (!token(index).getLexeme().equals("]") && !token(index).getLexeme().equals("}") && !token(index).getLexeme().equals(";")) {
            if (token(index).isVariable()) {
                ARGUMENTS.push(token(index).getLexeme());
            } else if (token(index).isOperator()) {
                while (OPERATIONS.size() !=0 && priority(OPERATIONS.peek()) <= priority(token(index).getLexeme())) {
                    generateCommand(OPERATIONS, ARGUMENTS);
                }
                OPERATIONS.push(token(index).getLexeme());
            } else if (token(index).getLexeme().equals("(")) {
                OPERATIONS.push(token(index).getLexeme());
            } else if (token(index).getLexeme().equals(")")) {
                while (!OPERATIONS.peek().equals("(")) {
                    generateCommand(OPERATIONS, ARGUMENTS);
                }
                OPERATIONS.pop();
            } else {
                System.err.println("Expression exception");
                System.exit(1);
            }
            index++;
        }
        while (OPERATIONS.size() != 0) {
            generateCommand(OPERATIONS, ARGUMENTS);
        }
        index++;
        if (args.length != 0) {
            COMMANDS.getLast().setArg3(args[0]);
            temp_count--;
        }
    }

}
