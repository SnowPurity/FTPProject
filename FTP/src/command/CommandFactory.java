package command;

public class CommandFactory {
    public static Command getCommand(String type)
    {
        type=type.toUpperCase();
        return switch (type) {
            case "USER" -> new UserCommand();
            case "PASS" -> new PassCommand();
            case "LIST" -> new LsCommand();
            case "RETR" -> new GetCommand();
            case "PORT" -> new PortCommand();
            case "QUIT" -> new QuitCommand();
            case "CWD" -> new CwdCommand();
            case "XPWD" -> new PwdCommand();
            case "MKD" -> new MkdirCommand();
            case "STOR" -> new StorCommand();
            case "NLST" -> new DirCommand();
            case "XRMD" -> new RmdirCommand();
            case "DELE" -> new DeleCommand();
            case "PASV" -> new PasvCommand();
            case "SYST" -> new SystCommand();
            case "APPE" -> new AppeCommand();
            default -> null;
        };
    }
}
