package iwishing.ccCommunity.community.DTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询分页的数据传输对象， 还需要增加前一页，总页数，还有帖子列表等信息
 */
@Data
public class QueryPaginDTO {
    //当前页号
    private int currentPage;
    //总页数
    private int totalPage;
    //查询出来的帖子列表
    private List<PostDTO> postList;
    //所有页的页号
    private List<Integer> pages = new ArrayList<Integer>();
    //是否有前一页按钮
    private boolean showPrevious;
    //是否有第一页按钮
    private boolean showFirstPage;
    //是否有下一页按钮
    private boolean showNext;
    //是否有最后一页按钮
    private boolean showEndPage;

    /**
     * 设置分页，需要的参数在该方法计算
     * @param totalCount 总记录数
     * @param page 当前页数
     * @param size 每页显示post数
     */
    public void setPagination(int totalCount, int page, int size) {
        //等于0代表每页size个，刚好显示完，不等于0代表多了几个，这个几个也要用一页显示，所有+1
        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        //假如某个社区没有帖子，总页数就为1
        if (totalCount == 0){
            totalPage = 1;
        }
        //设置当前页
        currentPage = page;
        //初始化页号列表
        pages.add(page);
        for (int i=1; i <= 3; i++){
            if(page - i> 0){
                //每次像index=0的位置插入
                pages.add(0,page-i);
            }
            if(page + i <= totalPage){
                pages.add(page+i);
            }
        }

        //当在第1页的时候，就不显示前一页按钮，在最后一页的时候，就不显示下一页按钮
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //如果页号列表存在第一页，则不显示第一页按钮，如果存在最后一页，则不显示最后一页按钮
        if(pages.contains(1) || totalPage == 1){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        if(pages.contains(totalPage) || totalPage == 1){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
