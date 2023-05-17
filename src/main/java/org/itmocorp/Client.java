package org.itmocorp;


import org.itmocorp.controller.commands.AbstractCommand;
import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Класс клиента Client
 *
 * @author Alexey Volkov P3113
 */
public class Client implements Runnable{
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private Selector selector;


    public Client() throws IOException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
    }

    /**
     * Класс main
     *
     * @param args аргументы командной строки
     */
    public static void main(String args[]){
        try{
            Client client = new Client();
            client.connect("localhost", 1555);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Пытаетеся присоединиться к серверу
     *
     * @param hostname имя сервера
     * @param port     порт
     * @throws IOException IOException
     */

    private void connect(String hostname, int port) throws IOException {
        socketAddress = new InetSocketAddress(hostname, port);
        datagramChannel.connect(socketAddress);
        System.out.println("Устанавливаем соединение с " + hostname + " по порту " + port);
    }

    /**
     * Получает ответ от сервера
     *
     * @return полученное сообщение
     * @throws IOException IOException
     */
    private String receiveAnswer() throws IOException {
        byte[] bytes = new byte[1000000];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketAddress = datagramChannel.receive(buffer);
        return new String(buffer.array()).split("�")[0].trim();
    }


    /**
     * Отправляет команду серверу
     *
     * @param command передаваемая команда
     * @throws IOException IOException
     */

    private void sendCommand(AbstractCommand command) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(command));
//      System.out.println("Отправляем команду " + command);
        datagramChannel.send(buffer, socketAddress);
        if (command != null)
            if (command.getClass().getName().contains("Exit"))
                System.exit(0);
    }


    /**
     * Отправляет объект spaceMarine серверу, как дополнительный аргумент для некоторых команд
     *
     * @param product объект класса Product
     * @throws IOException IOException
     */
    private void sendProduct(Product product) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(new Serialization().SerializeObject(product));
//        System.out.println("Отправляем объект " + spaceMarine);
        datagramChannel.send(buffer, socketAddress);
    }




    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isReadable()) {
//                        System.out.println("Readable");
                        String answer = receiveAnswer();
                        if (answer.contains("Конец ввода"))
                            datagramChannel.register(selector, SelectionKey.OP_WRITE);
                        else System.out.println(answer);
                    }
                    if (selectionKey.isWritable()) {
                        datagramChannel.register(selector, SelectionKey.OP_READ);
//                        System.out.println("Writable");
                        AbstractCommand command = CommandManager.CommandDeterminator(scanner.nextLine().trim().split("\\s+"));
                        sendCommand(command);
                        if (command != null && command.isNeedObjectToExecute()) {
                            sendProduct(CommandManager.getProduct());
                        }

                    }
                }
            }
        } catch (PortUnreachableException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить данные по указанному порту/сервер не доступен");
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
