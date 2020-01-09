import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        String stringBuilder = readFile("src/main/resources/code.txt");
        LinkedList<Token> tokens = tokenize(stringBuilder);
        tokens.forEach((x) -> System.out.print(x.getLexeme() + ' '));
        System.out.println("");

        Compiler compiler = new Compiler(tokens);
        compiler.compile();
        compiler.WriteToFile("src/main/resources/commands.txt");

        VirtualMachine virtualMachine = new VirtualMachine("src/main/resources/commands.txt");
        virtualMachine.GetComands();
        virtualMachine.execute();

    }

    private static String readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String code = "";
        while (scanner.hasNextLine()) {
            code = code.concat(scanner.nextLine());
        }
        return code;
    }

    private static LinkedList<Token> tokenize(String code) {
        String str = code.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("[a-zA-Z]+|\\d+\\.\\d+|\\d+|\\W");
        Matcher matcher = pattern.matcher(str);

        LinkedList<Token> tokens = new LinkedList<>();

        while (matcher.find()) {
            tokens.add(new Token(str.substring(matcher.start(), matcher.end())));
        }
        return tokens;
    }
}
