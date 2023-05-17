package org.itmocorp.controller.commands;


import org.itmocorp.controller.handlers.ScriptHandler;
import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.controller.handlers.InputHandler;
import org.itmocorp.model.data.Product;

public class Add extends AbstractCommand{

    public Add(){
        name = "add";
        help = "Команда позволяет добавить новый элемент в коллекцию";
        needObjectToExecute = true;
    }

    @Override
    public void execute() {
        CommandManager.printToClient("Была вызвана команда ADD");
        if (args.length == 0) {
            if (!CommandManager.isScriptStatus()) {
                if (product != null) {
                    CommandManager.collection.add(product);
                    CommandManager.printToClient("Новый объект был успешно добавлен в вашу коллекцию");
                }else{
                    CommandManager.printToClient("Новый объект не был добавлен в коллекцию, так не удалось его сформировать");
                }
            } else {
                Product product = ScriptHandler.getProductFromFile();
                if (product != null) {
                    CommandManager.collection.add(product);
                }else{
                    CommandManager.printToClient("Новый объект не был добавлен в коллекцию, так не удалось его сформировать");
                }
            }
        }else{
            CommandManager.printToClient("На данном этапе команда не принимает аргументы.");
        }
    }


}
