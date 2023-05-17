package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;


public class RemoveById extends AbstractCommand {
    public RemoveById(){
        name = "removeById";
        help = "Удалить элемент из коллекции по его id";
    }


    @Override
    public void execute() {
        if (args.length == 0) {
            System.out.println("Для выполнения данной команды требуется аргумент");
        } else {
            System.out.println("Была вызвана команда RemoveById");
            int id = Integer.parseInt(args[0]);
            if (id < CommandManager.collection.size() && id >= 0) {
                for (int i = 0; i < CommandManager.collection.size(); i++) {
                    if (CommandManager.collection.get(i).getId() == id) {
                        CommandManager.collection.remove(i);
                        System.out.println("Элемент c id " + id + "был успешно удален");
                    }
                }

            } else {
                System.out.println("Элемента по данному id в коллекции нет, поэтому удалить его не получится.");
            }
            System.out.println("Команда RemoveById закончила выполнение.");
        }
    }
}

