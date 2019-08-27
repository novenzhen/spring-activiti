package com.demo.activiti.springactiviti.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

import javax.servlet.annotation.WebFilter;

/**
 * @Author Administrator
 * @Description TODO
 * @Date 2019/8/20 10:34
 * @ClassName PutFilter
 **/
@Component
@WebFilter(urlPatterns = "/*", filterName = "putFilter")
@Order(Integer.MIN_VALUE)
public class PutFilter extends HttpPutFormContentFilter {

}
