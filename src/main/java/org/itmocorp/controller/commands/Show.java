package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;

public class Show extends AbstractCommand{
    public Show(){
        name = "show";
        help = "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(){
        if (args.length == 0){
//            System.out.println("Была вызвана команда Show");
            if (CommandManager.collection.size() == 0) {
                CommandManager.printToClient("На данный момент коллекция пуста");
            }else{
//                for (Product x : CommandManager.collection) {
//                    CommandManager.printToClient(x.toString());
//                }
                CommandManager.collection.stream()
                        .map(Product::toString)
                        .forEach(CommandManager::printToClient);
            }
        }else{
            CommandManager.printToClient("Данная команда не принимает аргументы.");
        }
    }
}
