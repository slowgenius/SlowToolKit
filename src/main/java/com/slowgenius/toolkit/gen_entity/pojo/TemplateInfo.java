package com.slowgenius.toolkit.gen_entity.pojo;

import com.slowgenius.toolkit.utils.StrUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 21:00:13
 */

public class TemplateInfo {

    private List<FieldInfo> fieldInfoList;

    private String packageInfo;

    private ClassInfo classInfo;


    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }


    public static class FieldInfo {

        private String name;

        private String type;

        private String comment;

        public FieldInfo(String name, String type, String comment) {
            this.comment = Optional.ofNullable(comment).orElse("");
            this.name = Optional.ofNullable(name).orElse("");
            this.type = Optional.ofNullable(type).orElse("");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }


    public static class ClassInfo {

        private String name;

        private String comment;

        public ClassInfo(String name, String comment) {
            this.name = StrUtils.firstLetterUpper(Optional.ofNullable(name).orElse(" "));
            this.comment = Optional.ofNullable(comment).orElse("");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

