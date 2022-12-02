package com.slowgenius.toolkit.base

import com.intellij.openapi.options.Configurable
import com.slowgenius.toolkit.swingUtil.SingleChoiceFrame
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * 插件配置项
 *
 * @author hty
 * date: 2022/11/9
 */
class SlowToolKitSetting : Configurable {

    private var component: JComponent = JPanel()

    private val configInfo = HashMap<String, SingleChoiceFrame?>()

    init {
        val state = VisibleConfig.getInstance()
        state.properties.forEach { (k: String, v: Boolean?) ->
            val singleChoice = SingleChoiceFrame(k, v)
            component.add(singleChoice)
            configInfo[k] = singleChoice
        }
    }

    override fun getDisplayName(): String {
        return "SlowToolKit"
    }

    override fun createComponent(): JComponent {
        return component
    }

    override fun isModified(): Boolean {
        return true
    }

    // 点击配置页面中的 apply 按钮或者 OK 按钮，会调用该方法，在该方法中保存配置
    override fun apply() {
        val properties = VisibleConfig.getInstance().state.properties
        properties.forEach { (k: String, v: Boolean?) ->
            if (configInfo[k] == null) {
                return@forEach
            }
            properties[k] = configInfo[k]!!.isSelected
        }
    }
}