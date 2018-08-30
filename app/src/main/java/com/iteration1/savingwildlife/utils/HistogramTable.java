package com.iteration1.savingwildlife.utils;


import android.graphics.Paint;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.bin.david.form.core.TableConfig;

@SmartTable(name = "Top 10 Items on the beach (2016/08/30 - 2018/08/31)", count = false)
public class HistogramTable {
    @SmartColumn(id = 1, name = "Item", align = Paint.Align.LEFT)
    private String name;

    @SmartColumn(id = 2, name = "Count", align = Paint.Align.LEFT)
    private float count;

    TableConfig config = new TableConfig();


    public HistogramTable(String name, float count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

}


