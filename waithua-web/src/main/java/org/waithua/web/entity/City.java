package org.waithua.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jch on 17/1/12.
 */
@Data
public class City implements Serializable{
    private static final long serialVersionUID = 5374820148374392288L;

    private Long id;

    private String name;

    private String state;

    private String country;
}
