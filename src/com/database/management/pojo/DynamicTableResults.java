/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.management.pojo;

import java.util.List;

/**
 *
 * @author syam
 */
public class DynamicTableResults {

    private String[] headers;
    private List<String[]> rows;

    public DynamicTableResults() {
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<String[]> getRows() {
        return rows;
    }

    public void setRows(List<String[]> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DynamicTableResults{" + "headers=" + headers + ", rows=" + rows + '}';
    }

}
