package org.example.Data;

public final class DataHolder {
    private Data data; // Datas qui seront stockées entre les controlleurs
    private final static DataHolder dataholder = new DataHolder(); //Gestionnaire de data

    public DataHolder() {}

    public static DataHolder getDataStock() {
        return dataholder;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }
}