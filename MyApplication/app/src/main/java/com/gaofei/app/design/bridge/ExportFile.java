package com.gaofei.app.design.bridge;

/**
 * Created by gaofei on 11/02/2018.
 */

public abstract class ExportFile {
    private DataSource mDataSource;

    public void setDataSource(DataSource source) {
        this.mDataSource = source;
    }

    public DataSource getDataSource() {
        return mDataSource;
    }

    public abstract void exportFile();

    protected void log(){
        System.out.println(getDataSource().getClass().getSimpleName());
        System.out.println(this.getClass().getSimpleName());
    }
}
