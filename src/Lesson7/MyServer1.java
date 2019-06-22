package Lesson7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer1 {
    public static void main(String[] args) {
        new Server();
        System.out.println("Ok");
    }
}

class Server {
    private final int PORT = 8189;
    private AuthService authService;
    private List<ClientHandler> peers;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    Server(){
        authService = new AuthService();
        peers = new CopyOnWriteArrayList<>();

        try {
            authService.start();
            serverSocket = new ServerSocket(8181);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    void broadcast(String message) {
        for (ClientHandler clientHandler : peers) {
            clientHandler.sendMsg(message);
        }
    }

    void subscribe(ClientHandler clientHandler) {
        peers.add(clientHandler);
    }

    void unsubscribe(ClientHandler clientHandler) {
        peers.remove(clientHandler);
    }
}
