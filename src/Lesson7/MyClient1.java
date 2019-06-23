package Lesson7;

import lib.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient1 extends JFrame {
    private final String ADDR = "localhost";
    private final int PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private Thread tLstnr;

    private JTextArea area;
    private JTextField msg;
    private JTextField nick;
    private JPanel topPan;

    public MyClient1(){
        drawGUI();
        openConnect();
    }

    private void openConnect() {
        tLstnr = new Thread(() -> {
            while (true) try {
                while (true) try {
                    socket = new Socket(ADDR, PORT);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("Hello");
                    break;
                } catch (IOException ioe) {
                    area.append("trying to connect...\n");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ie) { }
                }

                while (true) {
                    String strFromSrv = in.readUTF();
                    if (strFromSrv.equalsIgnoreCase("/authOK")) {
                        topPan.setVisible(false);
                        area.append("Server connected\n");
                    }
                    if (strFromSrv.equalsIgnoreCase("/end")) {
                        closeConnect();
                        break;
                    }
                    area.append("Server: " + strFromSrv + "\n");
                }
            } catch (IOException e) {
                area.append("Error reading from server\n");
                try {
                    closeConnect();
                    Thread.sleep(1500);
                } catch (InterruptedException ie) { }
                //e.printStackTrace();
            }
        });
        tLstnr.start();
    }

    private void closeConnect(){
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            in = null; out = null ;socket = null;
        } catch (IOException e) {
            System.out.println("Error close connection");
            //e.printStackTrace();
        }
    }

    private void drawGUI(){
        setBounds(600,100,400,400);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        nick = new JTextField();
        new TextPrompt("Введите ник", nick, TextPrompt.Show.ALWAYS);
        topPan = new JPanel(new BorderLayout());
        JButton btSendNick = new JButton("Ok");
        btSendNick.addActionListener(e -> sndNick());
        topPan.add(nick, BorderLayout.CENTER);
        topPan.add(btSendNick, BorderLayout.EAST);

        add(topPan, BorderLayout.NORTH);

        msg = new JTextField();
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel botomPan = new JPanel(new BorderLayout());
        JButton btSendMsg = new JButton("Send");
        btSendMsg.addActionListener(e -> sndMsg());
        msg.addActionListener(e -> sndMsg());
        botomPan.add(msg, BorderLayout.CENTER);
        botomPan.add(btSendMsg, BorderLayout.EAST);

        add(botomPan, BorderLayout.SOUTH);

        //при закрытии окна клиента остановить сервер и закрыть ресурсы
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if (socket != null && socket.isConnected()) out.writeUTF("/end");
                    //closeConnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private void sndNick(){
        if (socket != null && socket.isConnected() && !nick.getText().trim().isEmpty()){
            try {
//                area.append("Client: "+nick.getText()+"\n");
                out.writeUTF("/auth " + nick.getText());
//                nick.setText("");
//                nick.grabFocus();

            } catch (IOException e) {
                System.out.println("Error writing");
                e.printStackTrace();
            }
        }
    }

    private void sndMsg(){
        if (socket != null && socket.isConnected() && !msg.getText().trim().isEmpty()){
            try {
                area.append("Client: "+msg.getText()+"\n");
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
        SwingUtilities.invokeLater(MyClient1::new);
    }
}
