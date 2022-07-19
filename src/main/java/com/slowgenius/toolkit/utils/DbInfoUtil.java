package com.slowgenius.toolkit.utils;

import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 21:10:43
 */

public class DbInfoUtil {

    public static List<DasColumn> getDasColumnList(DbTable dbTable) {
        return new ArrayList<>(DasUtil.getColumns(dbTable).toList());
    }

}
