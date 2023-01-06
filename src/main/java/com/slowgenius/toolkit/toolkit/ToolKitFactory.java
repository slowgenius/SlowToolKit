package com.slowgenius.toolkit.toolkit;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.slowgenius.toolkit.toolkit.ui.JsonFormatter;
import com.slowgenius.toolkit.toolkit.ui.TimeUtil;
import com.slowgenius.toolkit.toolkit.ui.TranslatorUI;
import org.jetbrains.annotations.NotNull;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/21 20:56:30
 */

public class ToolKitFactory implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 获取内容工厂的实例
        ContentFactory contentFactory = ApplicationManager.getApplication().getService(ContentFactory.class);
        // 获取 ToolWindow 显示的内容
        Content translator = contentFactory.createContent(new TranslatorUI(project).getMain(), "多语互译", true);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(translator);
        Content jsonFormatter = contentFactory.createContent(new JsonFormatter(project).getMain(), "Json格式化", true);
        toolWindow.getContentManager().addContent(jsonFormatter);
        //时间戳转换
        Content timeUtil = contentFactory.createContent(new TimeUtil(project).getMain(), "时间转换工具", true);
        toolWindow.getContentManager().addContent(timeUtil);
        //
    }
}
