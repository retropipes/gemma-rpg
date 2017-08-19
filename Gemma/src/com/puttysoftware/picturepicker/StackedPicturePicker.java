package com.puttysoftware.picturepicker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;

public final class StackedPicturePicker {
    /**
     * @version 2.0.0
     */
    // Fields
    private BufferedImageIcon[] choices;
    private JLabel[] choiceArray;
    private Container pickerContainer;
    private ButtonGroup radioGroup;
    private JRadioButton[] radioButtons;
    int index;
    private Color savedCRCColor;
    private Color savedCHColor;
    private EventHandler handler;
    private int stackCount;
    private final int imageSize;

    // Constructor
    public StackedPicturePicker(BufferedImageIcon[] pictures, boolean[] enabled,
            Color choiceColor, int newStackCount, int placeholderSize) {
        this.imageSize = placeholderSize;
        this.savedCHColor = choiceColor;
        this.stackCount = newStackCount;
        this.handler = new EventHandler();
        this.radioGroup = new ButtonGroup();
        this.pickerContainer = new Container();
        this.updatePicker(pictures, enabled);
        this.index = 0;
        this.savedCRCColor = this.pickerContainer.getBackground();
    }

    // Methods
    public Container getPicker() {
        return this.pickerContainer;
    }

    public void changePickerColor(Color c) {
        this.pickerContainer.setBackground(c);
        for (int x = 0; x < this.choiceArray.length; x++) {
            this.choiceArray[x].setBackground(c);
            this.radioButtons[x].setBackground(c);
        }
        // Update saved colors
        this.savedCRCColor = c;
        this.savedCHColor = c;
    }

    public void disablePicker() {
        this.pickerContainer.setEnabled(false);
        this.pickerContainer.setBackground(Color.gray);
        for (int x = 0; x < this.radioButtons.length; x++) {
            this.radioButtons[x].setEnabled(false);
        }
    }

    public void enablePicker() {
        this.pickerContainer.setEnabled(true);
        this.pickerContainer.setBackground(this.savedCRCColor);
        for (int x = 0; x < this.radioButtons.length; x++) {
            this.radioButtons[x].setEnabled(true);
        }
    }

    public void updatePicker(BufferedImageIcon[] newImages, boolean[] enabled) {
        this.choices = newImages;
        this.pickerContainer.removeAll();
        int rows = this.choices.length / (this.stackCount / 2);
        int extra = this.choices.length % (this.stackCount / 2);
        if (extra != 0) {
            rows += 2;
        }
        this.radioButtons = new JRadioButton[this.choices.length];
        this.choiceArray = new JLabel[this.choices.length];
        this.pickerContainer.setLayout(new GridLayout(0, this.stackCount));
        int picCounter = 0;
        int radioCounter = 0;
        int rowCounter = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < this.stackCount; y++) {
                if (picCounter < this.choices.length) {
                    this.choiceArray[picCounter] = new JLabel("",
                            this.choices[picCounter], SwingConstants.LEFT);
                    this.choiceArray[picCounter].setOpaque(true);
                    this.choiceArray[picCounter]
                            .setBackground(this.savedCHColor);
                    this.pickerContainer.add(this.choiceArray[picCounter]);
                } else if (rowCounter == rows - 2) {
                    // Add spacer
                    JLabel spacer = new JLabel("",
                            new BufferedImageIcon(this.imageSize,
                                    this.savedCHColor),
                            SwingConstants.LEFT);
                    this.pickerContainer.add(spacer);
                }
                picCounter++;
            }
            rowCounter++;
            for (int y = 0; y < this.stackCount; y++) {
                if (radioCounter < this.choices.length) {
                    this.radioButtons[radioCounter] = new JRadioButton();
                    this.radioButtons[radioCounter]
                            .setHorizontalAlignment(SwingConstants.CENTER);
                    this.radioButtons[radioCounter].setOpaque(true);
                    this.radioButtons[radioCounter]
                            .setBackground(this.savedCHColor);
                    this.radioButtons[radioCounter].setActionCommand(
                            Integer.valueOf(radioCounter).toString());
                    this.radioGroup.add(this.radioButtons[radioCounter]);
                    this.radioButtons[radioCounter]
                            .addActionListener(this.handler);
                    this.radioButtons[x].setEnabled(enabled[x]);
                    this.pickerContainer.add(this.radioButtons[radioCounter]);
                } else if (rowCounter == rows - 1) {
                    // Add spacer
                    JLabel spacer = new JLabel("",
                            new BufferedImageIcon(this.imageSize,
                                    this.savedCHColor),
                            SwingConstants.LEFT);
                    this.pickerContainer.add(spacer);
                }
                radioCounter++;
            }
            rowCounter++;
        }
        for (int x = 0; x < this.choices.length; x++) {
            if (enabled[x]) {
                this.radioButtons[x].setSelected(true);
                this.index = x;
                break;
            }
        }
    }

    public void updatePickerLayout(int maxHeight) {
        int newPreferredWidth = this.pickerContainer.getLayout()
                .preferredLayoutSize(this.pickerContainer).width;
        int newPreferredHeight = Math.min(maxHeight, this.pickerContainer
                .getLayout().preferredLayoutSize(this.pickerContainer).height);
        this.pickerContainer.setPreferredSize(
                new Dimension(newPreferredWidth, newPreferredHeight));
    }

    public void selectLastPickedChoice(int lastPicked) {
        this.radioButtons[lastPicked].setSelected(true);
    }

    /**
     *
     * @return the index of the picture picked
     */
    public int getPicked() {
        return this.index;
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // Do nothing
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            // A radio button
            StackedPicturePicker.this.index = Integer.parseInt(cmd);
        }
    }
}