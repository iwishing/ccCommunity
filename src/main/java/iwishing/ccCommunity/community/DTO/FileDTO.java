package iwishing.ccCommunity.community.DTO;

import lombok.Data;

/**
 * 文件传输对象
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}
