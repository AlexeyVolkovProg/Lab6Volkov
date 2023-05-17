package org.itmocorp.controller.managers;

import org.itmocorp.controller.commands.*;
import org.itmocorp.controller.handlers.InputHandler;
import org.itmocorp.controller.handlers.ScriptHandler;
import org.itmocorp.model.data.Product;
import org.itmocorp.model.managers.CollectionManager;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class CommandManager {

    public static Map<String, AbstractCommand> commands = new HashMap<>(); // коллекция, которая содержит все доступные нам команды

    public static Vector<Product> collection = CollectionManager.getVectorProducts(); // коллекция, с которой идет работа

    private static CommandManager commandManager = new CommandManager(); // создаем объект класса CommandManager в самом нем
    public static Date collectionTime;
    private final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true; // показывает статус работы программы
    private static boolean ScriptStatus = false; // показывает работает ли сейчас скрипт или нет

    //далее идут поля добавленные для работы с серверной частью

    private static DatagramChannel serverDatagramChannel; // поле, хранящее DatagramChannel для общения с клиентом

    private static SocketAddress socketAddress;


    public static Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    private CommandManager() {
        collectionTime = new Date();
        commands.put("add", new Add());
        commands.put("executeScript", new ExecuteScript());
        commands.put("show", new Show());
        commands.put("updateId", new UpdateId());
        commands.put("addIfMax", new AddIfMax());
        commands.put("groupCountingById", new GroupCountingById());
        commands.put("countGreaterThanUnitOfMeasure", new CountGreaterThanUnitOfMeasure());
        commands.put("exit", new Exit());
        commands.put("removeFirst", new RemoveFirst());
        commands.put("removeById", new RemoveById());
        commands.put("info", new Info());
        commands.put("save", new Save());
        commands.put("clear", new Clear());
        commands.put("printDescending", new PrintDescending());
        commands.put("help", new Help());
        commands.put("shuffle", new Shuffle());
    }

    //метод, который поможет узнать вашу команду и приступить к ее выполнению(старый метод для работы с командами через одну консоль)
//    public void executeCommands() {
//        try {
//            while (isRunning) {
//                System.out.print("Введите команду: ");
//                String[] input = scanner.nextLine().trim().split("\\s+", 2);
//                String commandName = input[0];
//                String argument = input.length > 1 ? input[1] : "";
//                AbstractCommand command = commands.get(commandName);
//                if (command == null) {
//                    System.out.println("Команда \"" + commandName + "\" не найдена. Введите \"help\" для списка команд.");
//                } else {
//                    try {
//                        command.execute(argument);
//                    } catch (IllegalArgumentException e) {
//                        System.out.println("Ошибка выполнения команды \"" + commandName + "\": " + e.getMessage());
//                    }
//                }
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("Ошибка выполнения команды из-за введенного необрабатываемого символа");
//        }
//    }


    /**
     * Статический метод исполнения команды, полученной от клиента
     *
     * @param command         команда полученная от клиента
     * @param datagramChannel канал, использующийся для передачи сообщений клиенту
     * @param socketAddress   адрес порта
     */

    public static void ExecuteCommand(AbstractCommand command, DatagramChannel datagramChannel, SocketAddress socketAddress) throws IOException {
        commandManager.setServerDatagramChannel(datagramChannel);
        commandManager.setSocketAddress(socketAddress);
        //logger.info("Выполнение команды");
        command.execute();
        int i=0;
        while (i < 1000000) {
            i++;
        }
        if (!CommandManager.isScriptStatus()) {
            //logger.info("Отправляем клиенту сообщение о завершении чтения");
            datagramChannel.send(ByteBuffer.wrap("Конец ввода".getBytes()), socketAddress);
        }
    }


    /**
     * Метод помогает клиенту определить введенную команду
     * @param args
     * @return AbstractCommand
     */
    public static AbstractCommand CommandDeterminator(String[] args) {
        String cmd = args[0].trim();
        args = Arrays.copyOfRange(args, 1, args.length);
        AbstractCommand command = commands.get(cmd);
        if (command != null) {
            command.setArgs(args);
            return command;
        }
        return null;
    }


    /**
     * Метод использующийся, для создания объект при помощи пользовательского ввода от клиента
     * @return product
     */

    public static Product getProduct() {
        Product product;
        if (isScriptStatus())
            product = ScriptHandler.getProductFromFile();
        else
            product = InputHandler.ArgumentsReader();
        return product;
    }

    /**
     * Статический метод отвечающий за отправку сообщений клиенту
     *
     * @param line
     */
    public static void printToClient(String line) {
        try {
            Thread.sleep(3);
            ByteBuffer buffer = ByteBuffer.wrap((line.getBytes()));
            CommandManager.getServerDatagramChannel().send(buffer, CommandManager.getSocketAddress());
            //logger.info("Отправляем ответ клиенту: {} ", new String(buffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
            //logger.info("Не удалось отправить ответ клиенту {}", e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void setServerDatagramChannel(DatagramChannel serverDatagramChannel) {
        CommandManager.serverDatagramChannel = serverDatagramChannel;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        CommandManager.socketAddress = socketAddress;
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }

    public static void setScriptStatus(boolean scriptStatus) {
        ScriptStatus = scriptStatus;
    }

    public static boolean isScriptStatus() {
        return ScriptStatus;
    }

    public static DatagramChannel getServerDatagramChannel() {
        return serverDatagramChannel;
    }

    public static SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
