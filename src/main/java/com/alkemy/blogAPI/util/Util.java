package com.alkemy.blogAPI.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class Util {
    
    public boolean regexImage(String imageUrl) {
        String extension=imageUrl.substring(imageUrl.lastIndexOf("."));
        Pattern p = Pattern.compile("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$");
        Matcher page = p.matcher(imageUrl);
        return page.matches();
    }
}
