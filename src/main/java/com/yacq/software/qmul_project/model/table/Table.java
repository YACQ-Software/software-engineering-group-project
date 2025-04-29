package com.yacq.software.qmul_project.model.table;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tables")
public class Table {

    @Id
    private int tableId;
    private int size;
    private TableShape shape;
    private TablePosition position;
    private TableSection section;

    // Getters and Setters


    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TableShape getShape() {
        return shape;
    }

    public void setShape(TableShape shape) {
        this.shape = shape;
    }

    public TablePosition getPosition() {
        return position;
    }

    public void setPosition(TablePosition position) {
        this.position = position;
    }

    public TableSection getSection() {
        return section;
    }

    public void setSection(TableSection section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", size=" + size +
                ", shape=" + shape +
                ", position=" + position +
                ", section=" + section +
                '}';
    }
}