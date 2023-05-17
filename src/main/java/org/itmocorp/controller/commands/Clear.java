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
            System.out.println("Команда Clear начала выполнение.");
            CommandManager.collection.clear();
            System.out.println("Команда Clear закончила выполнение.");
        }else{
            System.out.println("Команда не принимает аргументы");
        }
    }
}
