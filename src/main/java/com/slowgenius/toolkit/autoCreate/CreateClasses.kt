package com.slowgenius.toolkit.autoCreate

import com.alibaba.fastjson.JSONObject
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.actions.CreateFileFromTemplateDialog.FileCreator
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.util.PlatformIcons
import com.slowgenius.toolkit.base.SlowAbstractAction
import com.slowgenius.toolkit.utils.SlowPsiUtils
import com.slowgenius.toolkit.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.util.*
import java.util.stream.Collectors

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/6/29 20:50:08
 */
open class CreateClasses : SlowAbstractAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.project!!
        val dataContext = anActionEvent.dataContext
        val view = LangDataKeys.IDE_VIEW.getData(dataContext)!!
        val dir = view.orChooseDirectory!!
        val builder = CreateFileFromTemplateDialog.createDialog(project)
        buildDialog(project, builder)
        builder.show("Error", "class", object : FileCreator<PsiElement> {
            override fun createFile(name: String, templateName: String): PsiElement {
                //小写字母开头直接创建包
                if (Character.isLowerCase(name[0])) return createPackage(dir, name)
                //获取类列表
                val classNameList: List<String> =
                    if (name.contains(","))
                        Arrays.stream(name.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                            .collect(Collectors.toList())
                    else listOf(name)
                //循环创建类文件
                val result = classNameList.stream()
                    .map { className: String? ->
                        JavaDirectoryService.getInstance().createClass(dir, className!!, templateName, true)
                    }.collect(Collectors.toList())
                //返回第一个元素
                val psiClass = result[0];
                val systemClipboardText = Utils.getSystemClipboardText().trim()
                if (!StringUtils.startsWith(systemClipboardText, "{")) {
                    return psiClass
                }
                val parseObject = JSONObject.parseObject(systemClipboardText);
                WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
                    parseObject.keys.forEach { item: String ->
                        val createPrimitiveType = PsiElementFactory.getInstance(project)
                            .createType(SlowPsiUtils.getClassByName(project, "java.lang.String"))
                        val field = PsiElementFactory.getInstance(project).createField(item, createPrimitiveType)
                        val comment = PsiElementFactory.getInstance(project).createDocCommentFromText("\n/**\n * \n */")
                        field.modifierList?.addBefore(comment, field.modifierList?.firstChild)
                        psiClass.add(field)
                        CodeStyleManager.getInstance(project).reformat(psiClass)
                    }
                }
                return psiClass

            }

            override fun startInWriteAction(): Boolean {
                return false
            }

            override fun getActionName(name: String, templateName: String): String {
                return "Create Something"
            }
        }) { createdElement: PsiElement? ->
            if (createdElement != null) view.selectElement(createdElement)
        }

    }

    private fun buildDialog(project: Project, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("Intelligence Create")
        builder.addKind("Class", PlatformIcons.CLASS_ICON, "Class")
        Arrays.stream(FileTemplateManager.getInstance(project).allTemplates)
            .forEach { item: FileTemplate ->
                //好像默认会有这个模版，遇到就跳过
                if (item.name == "JavaFXApplication") {
                    return@forEach
                }
                builder.addKind(item.name, null, item.name)
            }
        builder.setValidator(object : InputValidatorEx {
            override fun getErrorText(inputString: String): String? {
                //非法字符提示信息
                return null
            }

            override fun checkInput(inputString: String): Boolean {
                return true
            }

            override fun canClose(inputString: String): Boolean {
                return !StringUtil.isEmptyOrSpaces(inputString) && getErrorText(inputString) == null
            }
        })
    }

    private fun createPackage(directory: PsiDirectory?, packageName: String): PsiDirectory {
        return directory!!.createSubdirectory(packageName)
    }

    override fun getActionKey(): String {
        return "intelli create"
    }
}