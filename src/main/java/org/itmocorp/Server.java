package org.itmocorp;

import org.itmocorp.controller.commands.AbstractCommand;
import org.itmocorp.controller.managers.CommandManager;
import org.itmocorp.model.data.Product;
import org.itmocorp.model.managers.CollectionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Класс Server, отвечает за серверную часть нашего приложения
 *
 * @author Alexey Volkov, P3113
 */
public class Server implements Runnable{

    private static Logger LoggerFactory;
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());
    Selector selector; // пускай будет)
    private DatagramChannel datagramChannel; // канал для обмена информацией
    private SocketAddress socketAddress; // адрес определенного сокета
    private AbstractCommand command = null;
    private String[] args;

    private static Date creationDate; // дата, пускай будет на всякий

    private static int port = 1555;


    /**
     * Метод main
     *
     * @param args аргументы командной строки
    */
    public static void main(String[] args){
        Server server = new Server();
        server.setArgs(args);
        logger.info("Запускаем работу сервера по порту " + port);
        server.run();
    }

    /**
     * Метод receive позволяет получать и обрабатывать сообщения от клиента
     */
    private void receive() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100000000);
        byteBuffer.clear();
        socketAddress = datagramChannel.receive(byteBuffer);  // получаем адрес сокета, с которого была получена информация, null в случае если нет доступных данных для чтения
        byteBuffer.flip();   // переключаем buffer в режим записи
        if (socketAddress != null){
            Object object = new Serialization().DeserializeObject(byteBuffer.array()); // пытаемся десериализовать полученную команду
            if (object == null){
                //Получена несуществующая команда
                datagramChannel.send(ByteBuffer.wrap("Команда была не найдена или же вы ввели неверное кол-во аргументов. Введите help для просмотра команд".getBytes()), socketAddress);
                datagramChannel.send(ByteBuffer.wrap("Конец ввода".getBytes()), socketAddress);
                return;
            }
            if (!object.getClass().getName().contains(".Product")){
                command = (AbstractCommand) object;
                System.out.println(" Сервер получил команду: " + command.getName());
                // Сервер получил команду: " + command.getName()
                if (!command.isNeedObjectToExecute()){
                    //Команда не требует объекта для выполнения. Начинаем выполнение
                    CommandManager.ExecuteCommand(command, datagramChannel, socketAddress);
                }
            } else if (command != null){
                Product product = new Product((Product) object);
                command.setProduct(product);
                //logger.info("Получен объект {}, необходимый команде. Начинаем выполнение", product);
                CommandManager.ExecuteCommand(command, datagramChannel, socketAddress);
            }
        }
    }



    @Override
    public void run() {

        if (args.length == 0) {
            System.out.println("Не был указан файл");
        } else {
            String filePath = args[0];
            String fileSeparator = ",";
            CollectionManager collectionManager1 = new CollectionManager(filePath, fileSeparator);
            socketAddress = new InetSocketAddress(port);
            try {
                selector = Selector.open();
                datagramChannel = DatagramChannel.open();
                datagramChannel.bind(socketAddress);
                datagramChannel.configureBlocking(false);
                datagramChannel.register(selector, datagramChannel.validOps());
                //logger.info("Канал открыт и готов для приёма сообщений");
                while (true) {
                    receive();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
