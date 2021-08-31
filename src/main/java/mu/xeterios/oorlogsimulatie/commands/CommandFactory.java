package mu.xeterios.oorlogsimulatie.commands;

import mu.xeterios.oorlogsimulatie.commands.command.Create;
import mu.xeterios.oorlogsimulatie.commands.command.Forcestart;
import mu.xeterios.oorlogsimulatie.commands.command.Forcestop;
import mu.xeterios.oorlogsimulatie.commands.command.Setspawn;

public class CommandFactory {

    public ICmd GetCommand(String[] args){
        switch (args[0]){
            case "create":
                return new Create(args);
            case "forcestart":
                return new Forcestart(args);
            case "forcestop":
                return new Forcestop(args);
            case "setspawn":
                return new Setspawn(args);
            default:
                return null;
        }
    }
}
