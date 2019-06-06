package Lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Program4 {
    public static void main(String[] args) {
        new MyWindow();
        System.out.println("Ok");
    }

}

class MyWindow extends JFrame {
    public MyWindow() {
        setTitle("Chat window");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int sizeWidth = 400;
        int sizeHeigth = 300;

        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeigth) / 2;

        setBounds(locationX,locationY,sizeWidth,sizeHeigth);

        setLayout(null);

        JLabel chatLabel = new JLabel("Chat:");;
        chatLabel.setBounds(7,5,200,25);
        JTextArea areaChat = new JTextArea("", 1, 1);
        areaChat.setBounds(7,30,370,180);
        add(chatLabel);
        add(areaChat);

        JTextField msgField = new JTextField();
        msgField.setBounds(7,220,280,25);
        JButton jbt = new JButton("Ok");
        jbt.setBounds(297,220,80,25);
        jbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaChat.append(msgField.getText()+"\n");
                msgField.setText("");
            }
        });

        add(msgField);
        add(jbt);

        setVisible(true);
    }
}