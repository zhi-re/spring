package fun.chenqi.travel.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 */
public class PageBean<T> implements Serializable {
    private List<T> data;//当前页数据列表
    private int firstPage=1;//首页
    private int prePage;//上一页
    private int curPage;//当前页
    private int nextPage;//下一页
    private int totalPage;//总页数，或末页
    private int count;//总记录数
    private int pageSize;//每页大小，用于设置每页显示多少条数据
    private int startIndex;
    //代表页面中的起始页码
    private int startPage;
    //代表页面中的终止页码
    private int endPage;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    /**
     * 无参构造方法
     */
    public PageBean() {}

    public PageBean(int curPage ,int count,int pageSize) {
        this.curPage=curPage;
        this.count=count;
        this.pageSize=pageSize;

        startIndex=(curPage-1)*pageSize;

        //10 3 4
        //15 3 5
        totalPage=(count%pageSize==0)?(count/pageSize):(count/pageSize+1);

        if(curPage>1){
            prePage=curPage-1;
        }else{
            prePage=1;
        }

        if(curPage==totalPage){
            nextPage=totalPage;
        }else{
            nextPage=curPage+1;
        }

        startPage = curPage - 4;
        endPage = curPage+ 4;
        //看看总页数够不够9页
        if(totalPage>9){
            //超过了9页
            if(startPage < 1){
                startPage = 1;
                endPage = startPage+8;
            }
            if(endPage>totalPage){
                endPage = totalPage;
                startPage = endPage-8;
            }
        }else{
            //不够9页
            startPage = 1;
            endPage = totalPage;
        }
    }

    /**
     * 有参构造方法
     * @param data
     * @param firstPage
     * @param prePage
     * @param curPage
     * @param nextPage
     * @param totalPage
     * @param count
     * @param pageSize
     */
    public PageBean(List<T> data, int firstPage, int prePage, int curPage, int nextPage, int totalPage, int count, int pageSize) {
        this.data = data;
        this.firstPage = firstPage;
        this.prePage = prePage;
        this.curPage = curPage;
        this.nextPage = nextPage;
        this.totalPage = totalPage;
        this.count = count;
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getCurPage() {
        return curPage;
    }

    //封装当前页数据
    public void setCurPage(int curPage) {
        this.curPage = curPage;
        this.prePage = this.curPage-1;
        this.nextPage = this.curPage+1;
    }
    public int getNextPage() {
        this.nextPage = this.curPage+1;
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    //封装总记录数
    public void setCount(int count) {
        this.count = count;

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
