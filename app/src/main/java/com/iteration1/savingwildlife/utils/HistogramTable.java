package com.iteration1.savingwildlife.utils;


import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.Column;


@SmartTable(name = "Top 10 Items on the beach (2016/08/30 - 2018/08/31)")
public class HistogramTable{
    @SmartColumn(id = 1, name = "Item", align = Paint.Align.LEFT)
    private String name;

    @SmartColumn(id = 2, name = "Count", align = Paint.Align.LEFT, fixed = true)
    private int count;

    Column<Float> countColumn = new Column<Float>("Count", "count");


    public Column<Float> getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(Column<Float> countColumn) {
        this.countColumn = countColumn;
    }

    public HistogramTable(String name, int count) {

        this.name = name;
        this.count = count;
        countColumn.setAutoCount(true);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}


