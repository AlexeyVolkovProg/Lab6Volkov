package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;


public class RemoveById extends AbstractCommand {
    public RemoveById(){
        name = "removeById";
        help = "Удалить элемент из коллекции по его id";
    }


//    @Override
//    public void execute() {
//        if (args.length == 0) {
//            CommandManager.printToClient("Для выполнения данной команды требуется аргумент");
//        } else {
//            CommandManager.printToClient("Была вызвана команда RemoveById");
//            int id = Integer.parseInt(args[0]);
//            if (id < CommandManager.collection.size() && id >= 0) {
//                for (int i = 0; i < CommandManager.collection.size(); i++) {
//                    if (CommandManager.collection.get(i).getId() == id) {
//                        CommandManager.collection.remove(i);
//                        CommandManager.printToClient("Элемент c id " + id + "был успешно удален");
//                    }
//                }
//
//            } else {
//                CommandManager.printToClient("Элемента по данному id в коллекции нет, поэтому удалить его не получится.");
//            }
//            CommandManager.printToClient("Команда RemoveById закончила выполнение.");
//        }
//    }

    public void execute() {
        if (args.length == 0) {
            CommandManager.printToClient("Для выполнения данной команды требуется аргумент");
        } else {
            CommandManager.printToClient("Была вызвана команда RemoveById");
            int id = Integer.parseInt(args[0]);
            if (id < CommandManager.collection.size() && id >= 0) {
                CommandManager.collection.stream()
                        .filter(item -> item.getId() == id)
                        .findFirst()
                        .ifPresentOrElse(item -> {
                            CommandManager.collection.remove(item);
                            CommandManager.printToClient("Элемент c id " + id + " был успешно удален");
                        }, () -> {
                            CommandManager.printToClient("Элемента по данному id в коллекции нет, поэтому удалить его не получится.");
                        });
            } else {
                CommandManager.printToClient("Элемента по данному id в коллекции нет, поэтому удалить его не получится.");
            }
            CommandManager.printToClient("Команда RemoveById закончила выполнение.");
        }
    }
}

