package com.taotao.service.impl;

import com.taotao.common.pojo.ContentCategory;
import com.taotao.mapper.ContentCategoryMapper;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ContentCategoryServiceImpl
 *
 * @author eliefly
 * @date 2018-01-07
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory>
        implements ContentCategoryService {

    @Override
    public List<ContentCategory> queryContentCategoryByParentId(Long parentId) {

        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        return super.queryListByWhere(contentCategory);

    }

    @Override
    public ContentCategory saveContentCategory(ContentCategory contentCategory) {

        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        super.save(contentCategory);

        // 获取新增节点的父节点, 如果未置为父节点, 则更新为父节点
        ContentCategory parent = super.queryById(contentCategory.getParentId());
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            super.updateByIdSelective(parent);
        }

        return contentCategory;
    }

    @Override
    public void deleteContentCategory(Long parentId, Long id) {

        ArrayList<Object> deleteIds = new ArrayList<>();
        deleteIds.add(id);

        // 由id查询所有的子节点, 这些也是要删除的
        getChildId(deleteIds, id);
        super.deleteByIds(deleteIds);

        System.out.println(deleteIds);

        // 查询删除的节点的父节点是否还有子节点, 如果没有把父节点更新为非父节点
        ContentCategory category = new ContentCategory();
        category.setParentId(parentId);
        Integer count = super.queryCountByWhere(category);
        if (count == 0) {
            ContentCategory parent = new ContentCategory();
            parent.setId(parentId);
            parent.setIsParent(false);
            super.updateByIdSelective(parent);
        }

    }

    /**
     * 获取节点的子 ids
     *
     * @param deleteIds
     * @param id        当前节点 id
     */
    private void getChildId(ArrayList<Object> deleteIds, Long id) {
        ContentCategory contentCategory = new ContentCategory();

        // 根据父节点 id 查找
        contentCategory.setParentId(id);

        List<ContentCategory> list = super.queryListByWhere(contentCategory);

        if (list != null && !list.isEmpty()) {
            for (ContentCategory category : list) {
                deleteIds.add(category.getId());
                // 如果是父节点, 递归调用
                if (category.getIsParent()) {
                    getChildId(deleteIds, category.getId());
                }
            }
        }
    }
}
