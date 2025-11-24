package io.github.flashlack1314.quickstart.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * PageVO 测试类
 *
 * @author flash
 */
class PageVOTest {

    private List<String> testData;

    @BeforeEach
    void setUp() {
        testData = Arrays.asList("item1", "item2", "item3", "item4", "item5");
    }

    @Test
    void testCreateEmptyPage() {
        PageVO<String> emptyPage = PageVO.empty();

        assertNotNull(emptyPage);
        assertTrue(emptyPage.isEmpty());
        assertFalse(emptyPage.hasRecords());
        assertEquals(0, emptyPage.getCurrentPageSize());
        assertEquals(Collections.emptyList(), emptyPage.getRecords());
        assertEquals(0L, emptyPage.getTotal());
        assertEquals(1L, emptyPage.getCurrent());
        assertEquals(10L, emptyPage.getSize());
        assertEquals(0L, emptyPage.getPages());
    }

    @Test
    void testCreatePageWithBasicData() {
        PageVO<String> page = PageVO.of(testData, 100L, 2L, 10L);

        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertTrue(page.hasRecords());
        assertEquals(testData, page.getRecords());
        assertEquals(100L, page.getTotal());
        assertEquals(2L, page.getCurrent());
        assertEquals(10L, page.getSize());
        assertEquals(10L, page.getPages()); // (100 + 10 - 1) / 10 = 10
    }

    @Test
    void testCreatePageWithCustomPages() {
        PageVO<String> page = PageVO.of(testData, 100L, 2L, 10L, 15L);

        assertEquals(15L, page.getPages());
    }

    @Test
    void testPageNavigationMethods() {
        // Test middle page
        PageVO<String> middlePage = PageVO.of(testData, 100L, 5L, 10L);
        assertTrue(middlePage.hasNext());
        assertTrue(middlePage.hasPrevious());
        assertFalse(middlePage.isFirstPage());
        assertFalse(middlePage.isLastPage());

        // Test first page
        PageVO<String> firstPage = PageVO.of(testData, 100L, 1L, 10L);
        assertTrue(firstPage.hasNext());
        assertFalse(firstPage.hasPrevious());
        assertTrue(firstPage.isFirstPage());
        assertFalse(firstPage.isLastPage());

        // Test last page
        PageVO<String> lastPage = PageVO.of(testData, 50L, 5L, 10L);
        assertFalse(lastPage.hasNext());
        assertTrue(lastPage.hasPrevious());
        assertFalse(lastPage.isFirstPage());
        assertTrue(lastPage.isLastPage());

        // Test single page
        PageVO<String> singlePage = PageVO.of(testData, 5L, 1L, 10L);
        assertFalse(singlePage.hasNext());
        assertFalse(singlePage.hasPrevious());
        assertTrue(singlePage.isFirstPage());
        assertTrue(singlePage.isLastPage());
    }

    @Test
    void testChainSetters() {
        PageVO<String> page = new PageVO<String>()
                .setRecords(testData)
                .setTotal(100L)
                .setCurrent(2L)
                .setSize(20L)
                .setPages(5L);

        assertEquals(testData, page.getRecords());
        assertEquals(100L, page.getTotal());
        assertEquals(2L, page.getCurrent());
        assertEquals(20L, page.getSize());
        assertEquals(5L, page.getPages());
    }

    @Test
    void testGetCurrentPageSize() {
        PageVO<String> page = PageVO.of(testData, 100L, 1L, 10L);
        assertEquals(5, page.getCurrentPageSize());

        PageVO<String> emptyPage = PageVO.empty();
        assertEquals(0, emptyPage.getCurrentPageSize());
    }

    @Test
    void testResultVOIntegration() {
        // Test success with basic data
        ResultVO<PageVO<String>> result = PageVO.success(testData, 100L, 2L, 10L);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertNotNull(result.getData());

        PageVO<String> pageData = result.getData();
        assertEquals(testData, pageData.getRecords());
        assertEquals(100L, pageData.getTotal());
        assertEquals(2L, pageData.getCurrent());
        assertEquals(10L, pageData.getSize());
    }

    @Test
    void testResultVOEmpty() {
        ResultVO<PageVO<String>> result = PageVO.emptyResult();

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    void testNullRecords() {
        PageVO<String> page = new PageVO<String>();
        page.setRecords(null);

        assertTrue(page.isEmpty());
        assertFalse(page.hasRecords());
        assertEquals(0, page.getCurrentPageSize());
    }

    @Test
    void testZeroTotalRecords() {
        PageVO<String> page = PageVO.of(Collections.emptyList(), 0L, 1L, 10L);

        assertTrue(page.isEmpty());
        assertEquals(0L, page.getTotal());
        assertEquals(0L, page.getPages());
        assertFalse(page.hasNext());
        assertFalse(page.hasPrevious());
        assertTrue(page.isFirstPage());
        assertTrue(page.isLastPage());
    }

    @Test
    void testPageCalculationEdgeCases() {
        // Test exact division
        PageVO<String> page1 = PageVO.of(testData, 50L, 1L, 10L);
        assertEquals(5L, page1.getPages()); // 50 / 10 = 5

        // Test division with remainder
        PageVO<String> page2 = PageVO.of(testData, 51L, 1L, 10L);
        assertEquals(6L, page2.getPages()); // (51 + 10 - 1) / 10 = 6

        // Test single item per page
        PageVO<String> page3 = PageVO.of(testData, 5L, 1L, 1L);
        assertEquals(5L, page3.getPages()); // 5 / 1 = 5

        // Test all items in one page
        PageVO<String> page4 = PageVO.of(testData, 5L, 1L, 100L);
        assertEquals(1L, page4.getPages()); // (5 + 100 - 1) / 100 = 1
    }
}