package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;

import java.util.Collections;

public class Shuffle extends AbstractCommand{

    public Shuffle(){
        name = "shuffle";
        help = "Перемешивает элементы коллекции в случайном порядке.";
    }

    @Override
    public void execute() {
        if (args.length == 0){
            System.out.println("Команда Shuffle начала выполнение");
            Collections.shuffle(CommandManager.collection);
            System.out.println("Команда Shuffle закончила выполнение");
        }else{
            System.out.println("Данная команда не принимает аргументы");
        }
    }
}
