package my.btk.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PathInfo 存储 http 相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathInfo {
    /**
     * http 请求方法
     */
    private String httpMethod;

    /**
     * http 请求路径
     */
    private String httpPath;
}