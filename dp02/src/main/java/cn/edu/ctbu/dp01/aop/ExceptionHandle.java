package cn.edu.ctbu.dp01.aop;

import cn.edu.ctbu.dp01.exception.RException;
import cn.edu.ctbu.dp01.util.RUtil;
import cn.edu.ctbu.dp01.vo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cn.edu.ctbu.dp01.constant.REnum;

/**
 * 统一异常处理，使用ContollerAdvice(增强控制器)
 */
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public R handle(Exception e){
        if(e instanceof RException){
            RException rException = (RException) e;
            return RUtil.error(rException.getCode(),rException.getMessage());
        }
        return RUtil.error(REnum.UNKNOW_ERR);
    }

}
