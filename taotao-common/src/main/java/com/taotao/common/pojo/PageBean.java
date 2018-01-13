package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询对象
 *
 * @author eliefly
 * @date 2018-01-13
 */
public class PageBean<T> implements Serializable {

    List<T> rows = new ArrayList<>();

    /**
     * 总记录数, 由数据库获取
     */
    private int totalItems;

    /**
     * 单页显示的记录数, 由开发者指定.
     */
    private int pageItems;

    /**
     * 总页数. 由记录数和单页的记录数计算而得
     */
    private int totalPages;

    /**
     * 当前页码(请求参数)
     */
    private int curPage;

    /**
     * 构造方法. 可以不提供无参构造方法
     *
     * @param totalItems 总记录数
     * @param pageItems  单页显示记录数
     * @param curPage    当前页码
     */
    public PageBean(int totalItems, int pageItems, int curPage) {
        this.totalItems = totalItems;
        this.pageItems = pageItems;
        this.curPage = curPage;
        // 计算总页数
        this.totalPages = (int) Math.ceil((totalItems + 0.0) / pageItems);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getPageItems() {
        return pageItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 当前页码是可以设置的
     *
     * @param curPage 页码
     */
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getCurPage() {
        return curPage;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "rows=" + rows +
                ", totalItems=" + totalItems +
                ", pageItems=" + pageItems +
                ", totalPages=" + totalPages +
                ", curPage=" + curPage +
                '}';
    }
}