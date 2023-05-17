package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;
import org.itmocorp.model.managers.CollectionManager;

import java.util.*;
import java.util.stream.Collectors;

public class PrintDescending extends AbstractCommand {
    public PrintDescending(){
        name = "printDescending";
        help = "Выводит элементы коллекции в порядке убывания.";
    }
    @Override
    public void execute() {
        if (args.length == 0) {
            CommandManager.collection
                    .stream()
                    .sorted((p1, p2) -> (int) (p2.getPrice() - p1.getPrice()))
                    .forEach(System.out::println);
        }else{
            System.out.println("Данная команда не принимает аргументы");
        }
    }
}