//package com.inyaa.web.posts.service;
//
//import com.inyaa.web.posts.domain.vo.BlogMoveVO;
//import com.inyaa.web.posts.factory.BlogPlatformFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Service;
//
///**
// * @author: zsg
// * @description:
// * @date: 2020/4/5 21:39
// * @modified:
// */
//@Service
//public class WordPressService implements BlogPlatformService, InitializingBean {
//
//    String URL = "jdbc:mysql://%s:%s/%s?useSSL=false&characterEncoding=utf8";
//    String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
//
//    public String getUrl(BlogMoveVO blogMoveVO) {
//        return String.format(URL, blogMoveVO.getIp(), blogMoveVO.getPort(), blogMoveVO.getDatabase());
//    }
//
//    public String getCountSql(BlogMoveVO blogMoveVO) {
//        return "SELECT count(*) FROM wp_posts";
//    }
//
//    public String getQuerySql(BlogMoveVO blogMoveVO) {
//        return "SELECT post_title,post_content,post_date FROM wp_posts limit %s,%s";
//    }
//
//    public String getDriver() {
//        return MYSQL_DRIVER;
//    }
//
//    public void afterPropertiesSet() throws Exception {
//        BlogPlatformFactory.register("wordpress", this);
//    }
//}
