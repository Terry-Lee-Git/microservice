package com.imooc.course.filter;


import com.imooc.thrift.user.dto.UserDTO;
import com.imooc.user.client.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CourseFilter extends LoginFilter {

    @Value("${user.edge.service.addr}")
    private String userEdgeServiceAddr;

    @Override
    protected String userEdgeServiceAddr() {
        return userEdgeServiceAddr;
    }

    @Override
    protected void login(HttpServletRequest rq, HttpServletResponse rp, UserDTO userDTO) {
        rq.setAttribute("user",userDTO);
    }
}
