package com.secrething.common;

import com.secrething.common.core.Document;
import com.secrething.common.core.Key;
import lombok.Data;

/**
 * Created by liuzz on 2018-11-26 14:06.
 */
@Document(index = "adrift", type = "message")
@Data
public class Hello extends Message {

    @Key
    private boolean test = true;


}
