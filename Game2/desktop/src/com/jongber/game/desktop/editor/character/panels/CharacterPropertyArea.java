package com.jongber.game.desktop.editor.character.panels;

import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.editor.EditorCmd;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class CharacterPropertyArea implements EditorCmd.AreaImpl {

    Tuple2<JLabel, JTextArea> namePair = new Tuple2<>();

    Tuple2<JLabel, JSpinner> hpPair = new Tuple2<>();
    Tuple2<JLabel, JSpinner> mentalPair = new Tuple2<>();

    @Override
    public JPanel createPanel() {
        return null;
    }
}
