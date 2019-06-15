package Lesson6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    private Thread tLstnr;
    private boolean interapted = false;

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
        out.writeUTF("Hello");
        tLstnr = new Thread(() -> {
                while (!interapted) {
                    try {
                        String strFromSrv = in.readUTF();
                        if (strFromSrv.equalsIgnoreCase("/end")) {
                            closeConnect();
                            break;
                        }
                        if (area != null)
                        area.append("Server: "+strFromSrv+"\n");
                    } catch (IOException e) {
                        System.out.println("Error reading");
                        e.printStackTrace();
                    }
                }
            });
        tLstnr.start();
    }

    private void closeConnect(){
        try {
            interapted = true;
            tLstnr.interrupt();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error close connection");
            e.printStackTrace();
        }
    }

    private void drawGUI(){
        setBounds(600,100,400,400);
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
        add(botomPan, BorderLayout.SOUTH);

        //при закрытии окна клиента остановить сервер и закрыть ресурсы
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/end");
                    //closeConnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private void sndMsg(){
        if (!msg.getText().trim().isEmpty()){
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
        SwingUtilities.invokeLater(MyClient::new);
    }
}
