package org.waithua.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jch on 17/1/12.
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 3918141936570421025L;

    private Long id;
    private String name;
    private String password;
    private String sex;

}
