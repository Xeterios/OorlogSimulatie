package mu.xeterios.oorlogsimulatie.commands;

import mu.xeterios.oorlogsimulatie.commands.command.*;

public class CommandFactory {

    public ICmd GetCommand(String[] args){
        switch (args[0]){
            case "os":
            case "oorlogsimulatie":
                return new Default();
            case "create":
                return new Create(args);
            case "leave":
                return new Leave();
            case "forcestart":
                return new Forcestart(args);
            case "forcestop":
                return new Forcestop(args);
            case "setspawn":
                return new Setspawn(args);
            case "setteam":
                return new Setteam(args);
            case "maps":
                return new Maps();
            default:
                return null;
        }
    }
}
