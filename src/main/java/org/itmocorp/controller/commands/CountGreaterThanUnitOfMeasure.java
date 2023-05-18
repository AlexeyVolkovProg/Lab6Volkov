package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;
import org.itmocorp.model.data.UnitOfMeasure;


public class CountGreaterThanUnitOfMeasure extends AbstractCommand {
     public CountGreaterThanUnitOfMeasure(){
         name = "countGreaterThanUnitOfMeasure";
         help = "Выводит количество элементов, значение поля unitOfMeasure которых больше заданного зн-ия unitOfMeasure";
     }

    @Override
//    public void execute() {
//        int counter = 0;
//        if (args.length != 0) {
//            CommandManager.printToClient("Команда CountGreaterThanUnitOfMeasure начала свое выполнение");
//            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(args[0]);
//            for (Product product : CommandManager.collection) {
//                if (product.getUnitOfMeasure().compareTo(unitOfMeasure) > 0) {
//                    counter++;
//                }
//            }
//            CommandManager.printToClient("" + counter);
//            CommandManager.printToClient("Команда CountGreaterThanUnitOfMeasure начала свое выполнение");
//        }else{
//            CommandManager.printToClient("Не был указан аргумент для данной команды");
//        }
//
//    }
    public void execute() {
        if (args.length != 0) {
            CommandManager.printToClient("Команда CountGreaterThanUnitOfMeasure начала свое выполнение");
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(args[0]);

            long counter = CommandManager.collection.stream()
                    .filter(product -> product.getUnitOfMeasure().compareTo(unitOfMeasure) > 0)
                    .count();

            CommandManager.printToClient(String.valueOf(counter));
            CommandManager.printToClient("Команда CountGreaterThanUnitOfMeasure завершила свое выполнение");
        } else {
            CommandManager.printToClient("Не был указан аргумент для данной команды");
        }
    }

}
