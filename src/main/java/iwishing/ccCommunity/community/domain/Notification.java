package iwishing.ccCommunity.community.domain;

import lombok.Data;

/**
 * 通知类
 */
@Data
public class Notification {
    //通知id
    private int notificationId;
    //通知人
    private int notifier;
    //接受人
    private int receiver;
    //通知的内容id，可能是评论，也可能是回复
    private int outerId;
    //通知类型
    private int type;
    //通知时间
    private long gmtCreate;
    //通知状态
    private int status;
}
