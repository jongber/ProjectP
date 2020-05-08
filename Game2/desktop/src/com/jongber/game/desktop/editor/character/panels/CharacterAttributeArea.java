package com.jongber.game.desktop.editor.character.panels;

import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.editor.EditorCmd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class CharacterAttributeArea implements EditorCmd.AreaImpl {

    JPanel panel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();

    Tuple2<JLabel, JTextField> className;
    Tuple2<JLabel, JSpinner> maxHP;
    Tuple2<JLabel, JSpinner> maxMental;
    Tuple2<JLabel, JSpinner> attack;
    Tuple2<JLabel, JSpinner> armor;
    Tuple2<JLabel, JSpinner> accuracy;
    Tuple2<JLabel, JSpinner> evasion;
    Tuple2<JLabel, JSpinner> luck;
    Tuple2<JLabel, JSpinner> speed;

    public CharacterAttributeArea() {
        this.className = new Tuple2<>(new JLabel("class : "), new JTextField(8));
        this.maxHP = new Tuple2<>(new JLabel("MaxHP : "), new JSpinner(new SpinnerNumberModel(10, 1, 200, 1)));
        this.maxMental = new Tuple2<>(new JLabel("MaxMental : "), new JSpinner(new SpinnerNumberModel(10, 1, 100, 1)));
        this.attack = new Tuple2<>(new JLabel("Attack : "), new JSpinner(new SpinnerNumberModel(10, 1, 100, 1)));
        this.armor = new Tuple2<>(new JLabel("Armor : "), new JSpinner(new SpinnerNumberModel(10, 1, 100, 1)));
        this.accuracy = new Tuple2<>(new JLabel("Accuracy : "), new JSpinner(new SpinnerNumberModel(80, 1, 100, 1)));
        this.evasion = new Tuple2<>(new JLabel("Evasion : "), new JSpinner(new SpinnerNumberModel(40, 1, 100, 1)));
        this.luck = new Tuple2<>(new JLabel("Luck : "), new JSpinner(new SpinnerNumberModel(40, 1, 100, 1)));
        this.speed = new Tuple2<>(new JLabel("Speed : "), new JSpinner(new SpinnerNumberModel(10, 1, 100, 1)));
    }

    @Override
    public JPanel createPanel() {
        this.panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Attribute Area"));

        this.gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.attach(this.className.getItem1(), this.className.getItem2(), 0);
        this.attach(this.maxHP.getItem1(), this.maxHP.getItem2(), 1);
        this.attach(this.maxMental.getItem1(), this.maxMental.getItem2(), 2);
        this.attach(this.attack.getItem1(), this.attack.getItem2(), 3);
        this.attach(this.armor.getItem1(), this.armor.getItem2(), 4);
        this.attach(this.accuracy.getItem1(), this.accuracy.getItem2(), 5);
        this.attach(this.evasion.getItem1(), this.evasion.getItem2(), 6);
        this.attach(this.luck.getItem1(), this.luck.getItem2(), 7);
        this.attach(this.speed.getItem1(), this.speed.getItem2(), 8);

        return this.panel;
    }

    public void attach(JComponent item1, JComponent item2, int y) {
        gbc.gridx = 0;  gbc.gridy = y;
        this.panel.add(item1, this.gbc);

        gbc.gridx = 1;  gbc.gridy = y;
        this.panel.add(item2, this.gbc);
    }
}
