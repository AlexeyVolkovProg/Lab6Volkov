package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;

public class Clear extends AbstractCommand{

    public Clear(){
        name = "clear";
        help = "Очищает коллекцию";
    }
    @Override
    public void execute() {
        if (args.length == 0) {
            CommandManager.printToClient("Команда Clear начала выполнение.");
            CommandManager.collection.clear();
            CommandManager.printToClient("Команда Clear закончила выполнение.");
        }else{
            System.out.println("Команда не принимает аргументы");
        }
    }
}
