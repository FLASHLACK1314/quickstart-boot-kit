package io.github.flashlack1314.quickstart.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Collections;

/**
 * 分页响应结果类 (Page Value Object)
 * 用于封装分页查询的响应数据，提供统一的分页格式
 *
 * @param <T> 响应数据的泛型类型
 * @author flash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageVO<T> {

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 创建空分页结果
     *
     * @param <T> 泛型类型
     * @return 空分页结果
     */
    public static <T> PageVO<T> empty() {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(Collections.emptyList());
        pageVO.setTotal(0L);
        pageVO.setCurrent(1L);
        pageVO.setSize(10L);
        pageVO.setPages(0L);
        return pageVO;
    }

    /**
     * 创建分页结果
     *
     * @param records 数据列表
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页大小
     * @param <T>     泛型类型
     * @return 分页结果
     */
    public static <T> PageVO<T> of(List<T> records, Long total, Long current, Long size) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(total != null ? total : 0L);
        pageVO.setCurrent(current != null ? current : 1L);
        pageVO.setSize(size != null ? size : 10L);
        // 计算总页数
        Long pageSize = size != null ? size : 10L;
        Long totalCount = total != null ? total : 0L;
        Long pages = pageSize == 0 ? 0L : (totalCount + pageSize - 1) / pageSize;
        pageVO.setPages(pages);
        return pageVO;
    }

    /**
     * 创建分页结果（指定总页数）
     *
     * @param records 数据列表
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页大小
     * @param pages   总页数
     * @param <T>     泛型类型
     * @return 分页结果
     */
    public static <T> PageVO<T> of(List<T> records, Long total, Long current, Long size, Long pages) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(total != null ? total : 0L);
        pageVO.setCurrent(current != null ? current : 1L);
        pageVO.setSize(size != null ? size : 10L);
        pageVO.setPages(pages != null ? pages : 0L);
        return pageVO;
    }

    /**
     * 判断是否为空结果
     *
     * @return true if records is null or empty
     */
    public boolean isEmpty() {
        return records == null || records.isEmpty();
    }

    /**
     * 判断是否有数据
     *
     * @return true if records is not null and not empty
     */
    public boolean hasRecords() {
        return !isEmpty();
    }

    /**
     * 判断是否有下一页
     *
     * @return true if has next page
     */
    public boolean hasNext() {
        return current != null && pages != null && current < pages;
    }

    /**
     * 判断是否有上一页
     *
     * @return true if has previous page
     */
    public boolean hasPrevious() {
        return current != null && current > 1;
    }

    /**
     * 获取当前页数据量
     *
     * @return 当前页记录数
     */
    public int getCurrentPageSize() {
        return records == null ? 0 : records.size();
    }

    /**
     * 判断是否为第一页
     *
     * @return true if is first page
     */
    public boolean isFirstPage() {
        return current != null && current <= 1;
    }

    /**
     * 判断是否为最后一页
     *
     * @return true if is last page
     */
    public boolean isLastPage() {
        return current != null && pages != null && (current >= pages || pages == 0);
    }

    /**
     * 创建 ResultVO 包装的分页结果
     *
     * @param records 数据列表
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页大小
     * @param <T>     泛型类型
     * @return ResultVO包装的分页结果
     */
    public static <T> ResultVO<PageVO<T>> success(List<T> records, Long total, Long current, Long size) {
        PageVO<T> pageVO = of(records, total, current, size);
        return ResultVO.success(pageVO);
    }

    /**
     * 创建空分页结果的 ResultVO
     *
     * @param <T> 泛型类型
     * @return ResultVO包装的空分页结果
     */
    public static <T> ResultVO<PageVO<T>> emptyResult() {
        PageVO<T> pageVO = empty();
        return ResultVO.success(pageVO);
    }
}