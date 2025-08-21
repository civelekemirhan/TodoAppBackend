package com.emirhancivelek.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception<E> {

    private  String path;

    private Date createTime;

    private String hostName;

    private E message;

}
