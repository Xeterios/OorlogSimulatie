package mu.xeterios.oorlogsimulatie.commands.handlers;

import mu.xeterios.oorlogsimulatie.commands.PermissionType;
import org.bukkit.command.CommandSender;

import java.util.Dictionary;
import java.util.Hashtable;

public class PermissionHandler {

    private final CommandSender sender;
    public Dictionary<String, String> nodes;

    public PermissionHandler(CommandSender player){
        this.sender = player;
        this.nodes = new Hashtable<>();
        FillNodes();
    }

    public PermissionType CheckPermission(String arg) {
        try {
            String perm = GetNode(arg);
            if (sender.hasPermission(perm) || sender.hasPermission("os.admin")){
                return PermissionType.ALLOWED;
            }
            return PermissionType.NOPERM;
        } catch (NullPointerException | IllegalArgumentException e) {
            return PermissionType.UNKNOWN;
        }
    }

    public void FillNodes(){
        nodes.put("os", "os.help");
        nodes.put("oorlogsimulatie", "os.help");
        nodes.put("setspawn", "os.setspawn");
        nodes.put("setteam", "os.setteam");
        nodes.put("create", "os.create");
        nodes.put("forcestop", "os.forcestop");
        nodes.put("forcestart", "os.forcestart");
        nodes.put("maps", "os.maps");
    }

    public String GetNode(String input){
        if (nodes.get(input) != null){
            return nodes.get(input);
        }
        return null;
    }
}
