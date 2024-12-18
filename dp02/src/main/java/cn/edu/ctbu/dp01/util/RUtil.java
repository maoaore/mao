package cn.edu.ctbu.dp01.util;

import cn.edu.ctbu.dp01.constant.REnum;
import cn.edu.ctbu.dp01.vo.R;

public class RUtil { /*no usages new*/

    public static R success(Object object,Long count) { /*1 usage new*/
        R result = new R();
        result.setCode(0);
        result.setData(object);
        result.setCount(count);
        result.setMsg("成功");
        return result;
    }

    public static R success(Object object){
        return success(object,null);
    }
    public static R success() { /*no usages new*/
        return success(null);
    }

    public static R error(Integer code, String msg) { /*no usages new*/
        R result = new R();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static R error(REnum rEnum) { /*no usages new*/
        R result = new R();
        result.setCode(rEnum.getCode());
        result.setMsg(rEnum.getMsg());
        return result;
    }
}
