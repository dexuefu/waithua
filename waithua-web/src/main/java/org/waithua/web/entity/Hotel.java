package org.waithua.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jch on 17/1/12.
 */
@Data
public class Hotel implements Serializable {
    private static final long serialVersionUID = -3443346952940988288L;

    private Long city;

    private String name;

    private String address;

    private String zip;
}
