package Lesson6;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer extends JFrame {
//    private final String ADDR = "localhost";
    private final int PORT = 8888;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private JTextArea area;
    private JTextField msg;

    public MyServer(){
        drawGUI();
        openConnect();
    }

    private void openConnect() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8888)) {
                System.out.println("Сервер запущен, ждёт подключения...");
                socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                String strFromClient = in.readUTF();
                area.append("Client: "+strFromClient+"\n");
                out.writeUTF("Hello");
                while (true) {
                    try {
                        strFromClient = in.readUTF();
                        if (strFromClient.equalsIgnoreCase("/end")) {
                            System.out.println("Client disconnected");
                            out.writeUTF(strFromClient);
                            break;
                        }
                        area.append("Client: "+strFromClient+"\n");
                        //outPutStream.writeUTF("echo: " + strFromClient);
                    } catch (IOException e) {
                        System.out.println("Error reading");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error creating ServerSocket");
                e.printStackTrace();
            }
        }).start();
    }

    private void drawGUI(){
        setBounds(100,100,400,400);
        setTitle("Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        msg = new JTextField();
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel botomPan = new JPanel(new BorderLayout());
        JButton btSendMsg = new JButton("Send");
        btSendMsg.addActionListener(e -> sndMsg());
        msg.addActionListener(e -> sndMsg());
        botomPan.add(btSendMsg, BorderLayout.EAST);

        botomPan.add(msg, BorderLayout.CENTER);
        add(botomPan, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void sndMsg(){
        if (!msg.getText().trim().isEmpty()){
            try {
                area.append("Server: "+msg.getText()+"\n");
                out.writeUTF(msg.getText());
                msg.setText("");
                msg.grabFocus();
            } catch (IOException e) {
                System.out.println("Error writing");
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyServer::new);
    }
}
