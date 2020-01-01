
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Command {
    private String command;
    private String[] args = new String[3];

    public Command(String command, String arg1) {
        this.command = command;
        this.args[0] = arg1;
    }

    public Command(String command, String arg1, String arg2) {
        this.command = command;
        this.args[0] = arg1;
        this.args[1] = arg2;
    }

    @Override
    public String toString() {
        return command.toString() + ' ' + Arrays.toString(args);
    }
}
