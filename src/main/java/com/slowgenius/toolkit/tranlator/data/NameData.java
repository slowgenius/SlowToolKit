package com.slowgenius.toolkit.tranlator.data;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/21 20:44:14
 */
public class NameData {

    @Getter
    private String classTag;
    private String col;
    private String colBasic;
    private String colWebWord;
    private String dOO;
    private String dTO;
    private String doTag;
    private String dtoTag;
    private String enumBasic;
    private String enumTag;
    private String enumWebWord;
    private String enumsModel;
    private String pOLO;
    private String varBasic;
    private String varWebWord;
    private String variableModel;
    private String variableTag;

    public Set<String> getAllWords() {
        return allWords;
    }

    public void setAllWords(Set<String> allWords) {
        this.allWords = allWords;
    }

    public Set<String> allWords = new HashSet<>();


    public static NameData getData(String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        String post = HttpUtil.post("http://p8z.fun/c", jsonObject.toJSONString());


        NameData goods = JSONObject.parseObject(post, NameData.class);
        try {
            for (Field field : goods.getClass().getDeclaredFields()) {

                if (field.getName().equals("allWords")) {
                    continue;
                }
                if (field.get(goods) == null) {
                    continue;
                }
                goods.getAllWords().addAll(parseResult((String) field.get(goods)));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return goods;
    }

    public static HashSet parseResult(String result) {
        HashSet hashSet = new HashSet();
        Pattern pattern = Pattern.compile("data-clipboard-text='([^']*)'");
        Matcher matcher = pattern.matcher(result);

        System.out.println(result);

        System.out.println("================");
        while (matcher.find()) {
            hashSet.add(matcher.group().replaceAll("data-clipboard-text='([^']*)'", "$1"));
        }
        return hashSet;
    }


    @Override
    public String toString() {
        String result = "";
                if (classTag != null) result += "classTag='" + classTag + "\\n";
                if(col != null) result += "col: '" + col + "\\n" ;
                if(colBasic != null) result += "colBasic: '" + colBasic + "\\n" ;
                if(colWebWord != null) result += "colWebWord: '" + colWebWord + "\\n" ;
                if(dOO != null) result += "dOO: '" + dOO + "\\n" ;
                if(dTO != null) result += "dTO: '" + dTO + "\\n" ;
                if(doTag != null) result += "doTag: '" + doTag + "\\n" ;
                if(dtoTag != null) result += "dtoTag: '" + dtoTag + "\\n" ;
                if(enumBasic != null) result += "enumBasic: '" + enumBasic + "\\n" ;
                if(enumTag != null) result += "enumTag: '" + enumTag + "\\n" ;
                if(enumWebWord != null) result += "enumWebWord: '" + enumWebWord + "\\n" ;
                if(enumsModel != null) result += "enumsModel: '" + enumsModel + "\\n" ;
                if(pOLO != null) result += "pOLO: '" + pOLO + "\\n" ;
                if(varBasic != null) result += "varBasic: '" + varBasic + "\\n" ;
                if(varWebWord != null) result += "varWebWord: '" + varWebWord + "\\n" ;
                if(variableModel != null) result += "variableModel: '" + variableModel + "\\n" ;
                if(variableTag != null) result += "variableTag: '" + variableTag + "\\n";
        return result;
    }

    public String getClassTag() {
        return classTag;
    }

    public void setClassTag(String classTag) {
        this.classTag = classTag;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getColBasic() {
        return colBasic;
    }

    public void setColBasic(String colBasic) {
        this.colBasic = colBasic;
    }

    public String getColWebWord() {
        return colWebWord;
    }

    public void setColWebWord(String colWebWord) {
        this.colWebWord = colWebWord;
    }

    public String getdOO() {
        return dOO;
    }

    public void setdOO(String dOO) {
        this.dOO = dOO;
    }

    public String getdTO() {
        return dTO;
    }

    public void setdTO(String dTO) {
        this.dTO = dTO;
    }

    public String getDoTag() {
        return doTag;
    }

    public void setDoTag(String doTag) {
        this.doTag = doTag;
    }

    public String getDtoTag() {
        return dtoTag;
    }

    public void setDtoTag(String dtoTag) {
        this.dtoTag = dtoTag;
    }

    public String getEnumBasic() {
        return enumBasic;
    }

    public void setEnumBasic(String enumBasic) {
        this.enumBasic = enumBasic;
    }

    public String getEnumTag() {
        return enumTag;
    }

    public void setEnumTag(String enumTag) {
        this.enumTag = enumTag;
    }

    public String getEnumWebWord() {
        return enumWebWord;
    }

    public void setEnumWebWord(String enumWebWord) {
        this.enumWebWord = enumWebWord;
    }

    public String getEnumsModel() {
        return enumsModel;
    }

    public void setEnumsModel(String enumsModel) {
        this.enumsModel = enumsModel;
    }

    public String getpOLO() {
        return pOLO;
    }

    public void setpOLO(String pOLO) {
        this.pOLO = pOLO;
    }

    public String getVarBasic() {
        return varBasic;
    }

    public void setVarBasic(String varBasic) {
        this.varBasic = varBasic;
    }

    public String getVarWebWord() {
        return varWebWord;
    }

    public void setVarWebWord(String varWebWord) {
        this.varWebWord = varWebWord;
    }

    public String getVariableModel() {
        return variableModel;
    }

    public void setVariableModel(String variableModel) {
        this.variableModel = variableModel;
    }

    public String getVariableTag() {
        return variableTag;
    }

    public void setVariableTag(String variableTag) {
        this.variableTag = variableTag;
    }
}
