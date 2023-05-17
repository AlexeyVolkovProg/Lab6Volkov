package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;

public class Help extends AbstractCommand{
    public Help(){
        name = "help";
        help = "Выводит информацию о доступных командах";
    }

    @Override
    public void execute() {
        if (args.length == 0){
            for (AbstractCommand abstractCommand : CommandManager.getCommands().values()){
                System.out.println(abstractCommand.getName() + " " + abstractCommand.getHelp());
            }
        }else{
            System.out.println("Данная команда не принимает аргументы");
        }
    }
}
