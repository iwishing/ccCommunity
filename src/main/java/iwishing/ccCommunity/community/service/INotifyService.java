package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.NotifyPagination;
import iwishing.ccCommunity.community.domain.Notification;

/**
 * 通知业务层接口
 */
public interface INotifyService {

    //    保存通知
    public void saveNotify(Notification notification);

    /**
     * 根据用户id分页形式查询通知
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public NotifyPagination findNotificationByUesId(int userId, int page, int size);

    /**
     * 根据通知id，设置通知状态为已读
     * @param notificationId
     */
    public void updateNotificationStatus(int notificationId);


    /**
     *
     *
     * 根据用户id查询未读通知数
     * @param userId
     * @return
     */
    public int findUnreadNotificationByUserId(int userId);
}
