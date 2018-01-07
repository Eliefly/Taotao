package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * easyui使用表格的pojo
 *
 * @author eliefly
 * @create 2018-01-07 12:26
 */
public class EasyUIDataGridResult implements Serializable {

    private Long total;
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}

