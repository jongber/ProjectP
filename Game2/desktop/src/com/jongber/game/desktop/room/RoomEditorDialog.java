package com.jongber.game.desktop.room;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.room.event.CreateRoomEvent;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RoomEditorDialog {

    public static void popInitUI(GameLayer layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _popInitUI(layer);
            }
        }).start();
    }

    private static void popRoomUI(GameLayer layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _popRoomUI(layer);
            }
        }).start();
    }

    private static void _popInitUI(GameLayer layer) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new FlowLayout());
        dialog.setSize(100, 100);

        JButton newButton = new JButton();
        JButton loadButton = new JButton();

        newButton.setText("create room");
        loadButton.setText("load room");

        dialog.add(newButton);
        dialog.add(loadButton);

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layer.post(new CreateRoomEvent(layer));
                dialog.dispose();
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
            }
        });

        dialog.setVisible(true);
    }

    private static void _popRoomUI(GameLayer layer) {
        JDialog dialog = new JDialog();

        JPanel panel = new JPanel();

        dialog.add(panel);
        dialog.setSize(200, 400);
        dialog.setVisible(true);
    }
}
