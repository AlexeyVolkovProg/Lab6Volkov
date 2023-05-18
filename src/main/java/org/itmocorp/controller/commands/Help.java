package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;

public class Help extends AbstractCommand{
    public Help(){
        name = "help";
        help = "Выводит информацию о доступных командах";
    }

    @Override
//    public void execute() {
//        if (args.length == 0){
//            for (AbstractCommand abstractCommand : CommandManager.getCommands().values()){
//                CommandManager.printToClient(abstractCommand.getName() + " " + abstractCommand.getHelp());
//            }
//        }else{
//            CommandManager.printToClient("Данная команда не принимает аргументы");
//        }
//    }
    public void execute() {
        if (args.length == 0) {
            CommandManager.getCommands().values().stream()
                    .map(abstractCommand -> abstractCommand.getName() + " " + abstractCommand.getHelp())
                    .forEach(CommandManager::printToClient);
        } else {
            CommandManager.printToClient("Данная команда не принимает аргументы");
        }
    }

}
