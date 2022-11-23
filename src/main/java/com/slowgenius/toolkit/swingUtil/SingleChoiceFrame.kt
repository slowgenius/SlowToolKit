package com.slowgenius.toolkit.swingUtil

import javax.swing.ButtonGroup
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JRadioButton

/**
 * 单选框
 *
 * @author hty
 * date: 2022/11/23
 */
class SingleChoiceFrame : JPanel {
    private var radioButton1: JRadioButton
    private var radioButton2: JRadioButton
    private var jLabel: JLabel

    constructor(text: String?, selected: Boolean, button1Name: String?, button2Name: String?) {
        radioButton1 = JRadioButton(button1Name)
        radioButton2 = JRadioButton(button2Name)
        jLabel = JLabel(text)
        val group = ButtonGroup()
        group.add(radioButton1)
        group.add(radioButton2)
        this.add(jLabel)
        this.add(radioButton1)
        this.add(radioButton2)
        radioButton1.isSelected = selected
        radioButton2.isSelected = !selected
        this.isVisible = true
    }

    constructor(text: String?, selected: Boolean) {
        radioButton1 = JRadioButton("开启")
        radioButton2 = JRadioButton("关闭")
        jLabel = JLabel(text)
        val group = ButtonGroup()
        group.add(radioButton1)
        group.add(radioButton2)
        this.add(jLabel)
        this.add(radioButton1)
        this.add(radioButton2)
        radioButton1.isSelected = selected
        radioButton2.isSelected = !selected
        this.isVisible = true
    }

    val isSelected: Boolean
        get() = radioButton1.isSelected
}