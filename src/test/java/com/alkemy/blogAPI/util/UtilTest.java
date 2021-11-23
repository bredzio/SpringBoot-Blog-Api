package com.alkemy.blogAPI.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Autowired
    private Util util;

    @Test
    void regexImage() {
        util=new Util();
        String site="http://www.avajava.com/images/avajavalogo.jpg";
        boolean res=util.regexImage(site);
        Assertions.assertEquals(true,res);
    }
}