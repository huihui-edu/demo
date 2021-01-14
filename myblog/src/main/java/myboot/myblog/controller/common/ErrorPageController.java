package myboot.myblog.controller.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局错误处理
 */
@Controller
public class ErrorPageController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH,produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        //获取状态码
        HttpStatus status = getStatus(request);
        if (HttpStatus.BAD_REQUEST == status){
            //状态码是400---不正确的请求
            view.setViewName(ERROR_PATH + "/error_400");
            return view;
        }else if (HttpStatus.NOT_FOUND == status){
            view.setViewName(ERROR_PATH + "/error_404");
            return view;
        }else {
            view.setViewName(ERROR_PATH + "/error_5xx");
            return view;
        }
    }

    //获取状态码
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
