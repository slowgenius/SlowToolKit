package com.mp.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.mp.tools.ui.ConsoleUI;
import org.jetbrains.annotations.NotNull;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/21 20:56:30
 */

public class FindNameFactory implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(new ConsoleUI().getMain(), "查询命名", false);

        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);

    }
}
