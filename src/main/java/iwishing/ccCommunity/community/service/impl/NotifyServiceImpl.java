package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.NotificationDTO;
import iwishing.ccCommunity.community.DTO.NotifyPagination;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Notification;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.ICommentMapper;
import iwishing.ccCommunity.community.mapper.INotifyMapper;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.INotifyService;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("notifyService")
public class NotifyServiceImpl implements INotifyService {
    @Autowired
    private INotifyMapper notifyMapper;
    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private ICommentMapper commentMapper;
    /**
     * 保存通知
     * @param notification
     */
    @Override
    public void saveNotify(Notification notification) {
        notifyMapper.insertNotify(notification);
    }


    /**
     * 根据用户id分页形式查询通知
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public NotifyPagination findNotificationByUesId(int userId, int page, int size){
        //查询该用户的通知总数
        int totalCount = notifyMapper.findNumNotificationByUserId(userId);

        //开始封装queryPagingDTO
        NotifyPagination notifyPagination = new NotifyPagination();
        notifyPagination.setPagination(totalCount,page,size);
        //如果用户直接在url上输入page=-1或者大于totalpage的数，就设置容错一下
        if(page < 1){
            page = 1;
        }
        if(page > notifyPagination.getTotalPage()){
            page = notifyPagination.getTotalPage();
        }
        //计算查询的页号
        int queryPage = size*(page-1);
        System.out.println("userId-----"+userId);
        List<NotificationDTO> notificationDTOS = notifyMapper.findNotificationByUserId(userId,queryPage,size);
        for (NotificationDTO nf:notificationDTOS
             ) {
            if (nf.getNotifyType() == 0){
                //等于0是回复帖子
                    //查询帖子title
                nf.setNotifyOuterTitle(postMapper.findPostTitleById(nf.getNotifyOuterId()));
            }else{
                //等于1是回复评论
                    //查询评论，取前10个字符做标题
                String commentTitle = commentMapper.findCommentTitleById(nf.getNotifyOuterId());

                if (commentTitle.length()>30){
                    nf.setNotifyOuterTitle(commentTitle.substring(0,31));
                }else if (commentTitle.length() < 30){
                    nf.setNotifyOuterTitle(commentTitle.substring(0,commentTitle.length()));
                }
            }
        }
        notifyPagination.setNotificationDTOS(notificationDTOS);
        return notifyPagination;
    }

    /**
     * 根据通知id，设置通知状态为已读
     * @param notificationId
     */
    @Override
    public void updateNotificationStatus(int notificationId){
        notifyMapper.updateNotificationStatus(notificationId);
    }

    /**
     *
     *
     * 根据用户id查询未读通知数
     * @param userId
     * @return
     */
    @Override
    public int findUnreadNotificationByUserId(int userId){
        return notifyMapper.findUnreadNotificationByUserId(userId);
    }
}
