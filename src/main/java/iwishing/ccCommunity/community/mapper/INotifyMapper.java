package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.NotificationDTO;
import iwishing.ccCommunity.community.domain.Notification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知持久层接口
 */
@Repository
public interface INotifyMapper {
//    保存通知
    public void insertNotify(Notification notification);

    /**
     * 查询通知总数
     * @return
     */
    public int findNumNotificationByUserId(int userId);

    /**
     * 根据用户id查询通知
     * @param userId
     * @param queryPage
     * @param size
     * @return
     */
    public List<NotificationDTO> findNotificationByUserId(@Param(value = "userId") int userId, @Param(value = "queryPage") int queryPage, @Param(value = "size") int size);


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
