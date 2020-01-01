import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Compiler {
    private int index;
    private int temp_count;
    private LinkedList<Token> tokens = new LinkedList<>();
    private LinkedList<Command> COMMANDS = new LinkedList<>();



    public Compiler(LinkedList<Token> tokens) {
        index = 0;
        this.tokens = tokens;
        temp_count=1;
    }

    private Token token(int indx){
        return tokens.get(indx);
    }

    public void compile() {

    }


    private void HandleBlock() {
        while (index < tokens.size()) {
            if (token(index).isOperatorBlock()) {
                HandleOperatorBlock();
            } else {
                HandleCommand();
            }
            index++;
        }
    }

    private void HandleOperatorBlock() {



    }

    private void HandleCommand() {
        if (token(index).isIO()) {
            COMMANDS.add(new Command(token(index).getLexeme().toUpperCase(), token(index += 2).getLexeme()));
        }else {
            if(token(index+3).getLexeme().equals(";")){
                COMMANDS.add(new Command(token(index).getLexeme(),token(index+=2).getLexeme()));
            }else {
                HandleExpression(token(index).getLexeme());
            }
        }
        index+=2;
    }


    private void HandleExpression(String var) {

    }

    public void WriteToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        COMMANDS.forEach((p)->printWriter.println(p.toString()));
        printWriter.close();
    }


}
