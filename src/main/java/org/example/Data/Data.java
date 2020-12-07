package org.example.Data;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Object> datas = new ArrayList<>();

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas.addAll(datas);
    }
}