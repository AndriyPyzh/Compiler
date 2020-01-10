import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


@FunctionalInterface
interface Operation {
    public void apply(String[] args);
}

public class VirtualMachine {
    private int index;
    private LinkedList<Command> commands;
    private HashMap<String, Operation> operations = new HashMap<>();
    private HashMap<String, Double> variables = new HashMap<>();

    public VirtualMachine(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        LinkedList<String> commands = new LinkedList<>();
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine());
        }
        this.commands = new LinkedList<>();
        for (String command : commands) {
            String[] args = command.split(" ");
            switch (args.length) {
                case 2:
                    this.commands.add(new Command(args[0], args[1]));
                    break;
                case 3:
                    this.commands.add(new Command(args[0], args[1], args[2]));
                    break;
                case 4:
                    this.commands.add(new Command(args[0], args[1], args[2], args[3]));
                    break;
                default:
                    System.err.println("Wrong Commands");
            }
        }
        createoperations();
    }

    public void GetComands() {
        System.out.println(commands);
    }


    public static void main(String... args) throws FileNotFoundException {
        VirtualMachine virtualMachine = new VirtualMachine("src/main/resources/commands.txt");
        virtualMachine.GetComands();
        virtualMachine.execute();

    }

    public void execute() {
        for (; index < commands.size(); index++) {
            operations.get(commands.get(index).getCommand()).apply(commands.get(index).getArgs());
        }
    }

    private String var(String var){
        if (var.chars().allMatch(Character::isDigit))
            return var;
        return Integer.toString(variables.get(var).intValue());
    }

    private void add(String[] args) {
        variables.put(args[2], (double) (Integer.parseInt(var(args[0])) + Integer.parseInt(var(args[1]))));
    }

    private void sub(String[] args) {
        variables.put(args[2], (double) (Integer.parseInt(var(args[0])) - Integer.parseInt(var(args[1]))));
    }

    private void div(String[] args) {
        variables.put(args[2], (double) (Integer.parseInt(var(args[0])) / Integer.parseInt(var(args[1]))));
    }

    private void mul(String[] args) {
        variables.put(args[2], (double) (Integer.parseInt(var(args[0])) * Integer.parseInt(var(args[1]))));
    }

    private void write(String[] args) {
        System.out.println(variables.get(args[0]));
    }

    private void read(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String var = scanner.next();
        variables.put(args[0], Double.parseDouble(var));
    }

    private void copy(String[] args) {
        variables.put(args[1], Double.parseDouble(args[0]));

    }

    private void gotoifnot(String[] args) {
        if (variables.get(args[0]) == 0)
            index = Integer.parseInt(args[1]) - 1;
    }

    private void goto_(String[] args) {
        index = Integer.parseInt(args[0]) - 1;
    }



    private void createoperations() {
        operations.put("ADD", this::add);
        operations.put("SUB", this::sub);
        operations.put("DIV", this::div);
        operations.put("MUL", this::mul);
        operations.put("READ", this::read);
        operations.put("WRITE", this::write);
        operations.put("COPY", this::copy);
        operations.put("GOTOIFNOT", this::gotoifnot);
        operations.put("GOTO", this::goto_);
    }


}
