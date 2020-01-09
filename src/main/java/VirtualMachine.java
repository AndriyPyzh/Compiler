import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class VirtualMachine {
    private int index;
    private LinkedList<Command> commands;

    public VirtualMachine(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        LinkedList<String> commands = new LinkedList<>();
        while (scanner.hasNextLine()){
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
    }

    public void GetComands(){
        System.out.println(commands);
    }

    public static void main(String... args) throws FileNotFoundException {
        VirtualMachine virtualMachine = new VirtualMachine("src/main/resources/commands.txt");
        virtualMachine.GetComands();
    }
}
