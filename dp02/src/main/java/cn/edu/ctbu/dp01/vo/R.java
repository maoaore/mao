package cn.edu.ctbu.dp01.vo;

import lombok.Data;

/**
 *
 * @param <T>
 */
@Data
public class R <T>{
    private Integer code;
    private Long count;
    private String msg;
    private T data;
}
