package Lesson7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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
    private List<String> nicksLst;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    Server(){
        authService = new AuthService();
        peers = new CopyOnWriteArrayList<>();
        nicksLst = new CopyOnWriteArrayList<>();

        try {
            authService.start(nicksLst);
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket, authService);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            authService.stop();
        }
    }

    public void sendListTo(String nick) {
        StringBuffer lst = new StringBuffer();
        for (String nk : nicksLst) {
            lst.append(nk+", ");
        }
        if (lst.length()>0) lst.setLength(lst.length()-2);
        String msg = lst.toString();
        sendMsgTo(nick, msg);
    }

    void sendMsgTo(String nick, String msg) {
        for (ClientHandler clientHandler : peers) {
            if (clientHandler.getNick().equals(nick)) {
                clientHandler.sendMsg(msg);
                break;
            }
        }
    }

    void broadcast(String nick, String msg) {
        for (ClientHandler clientHandler : peers) {
            if (!clientHandler.getNick().equals(nick))
                clientHandler.sendMsg(msg);
        }
    }

    void subscribe(ClientHandler clientHandler, String nick) {
        peers.add(clientHandler);
        nicksLst.add(nick);
        broadcast(nick,nick+" connected");
    }

    void unsubscribe(ClientHandler clientHandler, String nick) {
        peers.remove(clientHandler);
        nicksLst.remove(nick);
        broadcast(nick,nick+" leave chat");
    }

}
