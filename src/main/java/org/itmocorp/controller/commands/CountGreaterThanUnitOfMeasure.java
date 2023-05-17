package org.itmocorp.controller.commands;

import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;
import org.itmocorp.model.data.UnitOfMeasure;


// todo проверь на вход аргумент
public class CountGreaterThanUnitOfMeasure extends AbstractCommand {
     public CountGreaterThanUnitOfMeasure(){
         name = "countGreaterThanUnitOfMeasure";
         help = "Выводит количество элементов, значение поля unitOfMeasure которых больше заданного зн-ия unitOfMeasure";
     }

    @Override
    public void execute() {
        int counter = 0;
        if (args.length != 0) {
            System.out.println("Команда CountGreaterThanUnitOfMeasure начала свое выполнение");
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(args[0]);
            for (Product product : CommandManager.collection) {
                if (product.getUnitOfMeasure().compareTo(unitOfMeasure) > 0) {
                    counter++;
                }
            }
            System.out.println(counter);
            System.out.println("Команда CountGreaterThanUnitOfMeasure начала свое выполнение");
        }else{
            System.out.println("Не был указан аргумент для данной команды");
        }

    }
}
