package Lesson8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient2 extends JFrame {
    private final String ADDR = "localhost";
    private final int PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;

    private Thread tLstnr;

    private JTextArea area;
    private JTextField msg;
    private JTextField nick;
    private JPanel topPan;

    public MyClient2(){
        drawGUI();
        openConnect();
    }

    private void openConnect() {
        tLstnr = new Thread(() -> {
            while (true) try {
                while (true) try {
                    socket = new Socket(ADDR, PORT);
                    area.append("connect to server succesfull\n");
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("Hello");
                    break;
                } catch (IOException ioe) {
                    area.append("trying to connect...\n");
                    try {
                        Thread.sleep(3000);
                        if (!topPan.isVisible()) topPan.setVisible(true);
                    } catch (InterruptedException ie) { }
                }

                while (true) {
                    String strFromSrv = in.readUTF();
                    if (strFromSrv.equalsIgnoreCase("/authOK")) {
                        nickName = nick.getText();
                        topPan.setVisible(false);
                        area.append("Server connected\n");
                    } else
                    if (strFromSrv.equalsIgnoreCase("/end")) {
                        closeConnect();
                        break;
                    } else
                        area.append(strFromSrv + "\n");
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

        //при закрытии окна клиента отключится от сервера и закрыть ресурсы
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
                out.writeUTF("/auth " + nick.getText());
            } catch (IOException e) {
                System.out.println("sndNick Error writing");
                //e.printStackTrace();
            }
        }
    }

    private void sndMsg(){
        if (socket != null && socket.isConnected() && !msg.getText().trim().isEmpty()){
            try {
                String str = msg.getText();
                if (str.startsWith("/w") || str.startsWith("/list")) {
                    out.writeUTF(str);
                } else {
                    area.append("Client: " + str + "\n");
                    out.writeUTF(nickName + ": " + str);
                }
                msg.setText("");
                msg.grabFocus();
            } catch (IOException e) {
                System.out.println("sndMsg Error writing");
                //e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyClient2::new);
    }
}
