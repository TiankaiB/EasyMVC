package my.btk.mvc;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet 所有 http 请求都由此 Servlet 转发
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private ControllerHandler controllerHandler = new ControllerHandler();

    private ResultRender resultRender = new ResultRender();

    /**
     * 执行请求
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码方式
        req.setCharacterEncoding("UTF-8");
        //获取请求方法和请求路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        log.info("[DoodleConfig] {} {}", requestMethod, requestPath);
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }

        ControllerInfo controllerInfo = controllerHandler.getController(requestMethod, requestPath);
        log.info("{}", controllerInfo);
        if (null == controllerInfo) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resultRender.invokeController(req, resp, controllerInfo);
    }
}
