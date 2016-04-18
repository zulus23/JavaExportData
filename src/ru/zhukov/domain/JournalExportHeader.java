package ru.zhukov.domain;

/**
 * Created by Gukov on 18.04.2016.
 */
public class JournalExportHeader {
    private long recNo;
    private int state;
    private String importBatch;
    private String systemId;
    private String parentRecId; //поле для связи с детальной таблицей
    private String name;
    private String dimension;


    public long getRecNo() {
        return recNo;
    }

    public void setRecNo(long recNo) {
        this.recNo = recNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImportBatch() {
        return importBatch;
    }

    public void setImportBatch(String importBatch) {
        this.importBatch = importBatch;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getParentRecId() {
        return parentRecId;
    }

    public void setParentRecId(String parentRecId) {
        this.parentRecId = parentRecId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() {
        return "JournalExportHeader{" +
                "recNo=" + recNo +
                ", state=" + state +
                ", importBatch='" + importBatch + '\'' +
                ", systemId='" + systemId + '\'' +
                ", parentRecId='" + parentRecId + '\'' +
                ", name='" + name + '\'' +
                ", dimension='" + dimension + '\'' +
                '}';
    }
}
