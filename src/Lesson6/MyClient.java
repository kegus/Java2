package Lesson6;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient extends JFrame {
    private final String ADDR = "localhost";
    private final int PORT = 8888;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private JTextArea area;
    private JTextField msg;

    public MyClient(){
        try {
            openConnect();
        } catch (IOException e) {
            System.out.println("Error connection");
            e.printStackTrace();
        }
        drawGUI();
    }

    private void openConnect() throws IOException {
        socket = new Socket(ADDR, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
                while (true) {
                    try {
                        String strFromSrv = in.readUTF();
                        if (strFromSrv.equalsIgnoreCase("/end")) {
                            closeConnect();
                            break;
                        }
                        area.append(strFromSrv+"\n");
                    } catch (IOException e) {
                        System.out.println("Error reading");
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    private void closeConnect(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error close connection");
            e.printStackTrace();
        }
    }

    private void drawGUI(){
        setBounds(100,100,600,600);
        setTitle("Client");
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
    }

    private void sndMsg(){
        if (!msg.getText().trim().isEmpty()){
            try {
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

    }
}
