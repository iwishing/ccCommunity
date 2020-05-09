package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.User;
import lombok.Data;

/**
 * 通知类
 */
@Data
public class NotificationDTO {
    //通知的id
    private int notificationId;
    //通知评论了什么，或这回复了哪个帖子的id
    private int notifyOuterId;
    //通知评论了什么，或这回复了哪个帖子的title
    private String notifyOuterTitle;
    //通知的类型
    private int notifyType;
    //通知创建时间
    private long gmtCreate;
    //通知的状态
    private int status;
    //通知人
    private User notifier;
}
