import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Command {
    private String command;
    private String arg1, arg2, arg3;

    public Command(String command, String arg1) {
        this.command = command;
        this.arg1 = arg1;
    }

    public Command(String command, String arg1, String arg2) {
        this.command = command;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public Command(String command, String arg1, String arg2, String arg3) {
        this.command = command;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    @Override
    public String toString() {
        return command.toString() + ' ' + arg1 + (arg2 == null ? "" :" " + arg2 + (arg3 == null ? "" :" " + arg3));
    }
}
