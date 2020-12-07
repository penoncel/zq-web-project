package com.mer.common.QuartzJob;

import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ATestTimes {

    public void getNowTimes(){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("|->"+ df.format(day));

    }

}
