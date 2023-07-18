package com.slowgenius.toolkit.base

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.slowgenius.toolkit.autoCreate.CreateClasses
import java.util.*
import kotlin.collections.HashMap

/**
 * 插件是否可见配置
 *
 * @author hty
 * date: 2022/11/9
 */
@State(name = "visibleConfig", storages = [Storage(value = "visibleConfig.xml")])
class VisibleConfig : PersistentStateComponent<VisibleConfig?> {
    var properties: MutableMap<String, Boolean> = HashMap()

    override fun getState(): VisibleConfig {
        return this
    }

    override fun loadState(visibleConfig: VisibleConfig) {
        properties = visibleConfig.properties
    }

    private fun init() {
        properties = HashMap()
        val action = (Optional.ofNullable(ActionManager.getInstance()).map { item-> item.getAction("intelli create") }.orElse(null) as CreateClasses)
        properties[action.getActionKey()] = true
    }

    companion object {
        @JvmStatic
           fun getInstance(): VisibleConfig {
                val visibleConfig = ApplicationManager.getApplication().getService(VisibleConfig::class.java)
                if (visibleConfig.properties.isEmpty()) {
                    visibleConfig.init()
                }
                return visibleConfig
            }
    }
}