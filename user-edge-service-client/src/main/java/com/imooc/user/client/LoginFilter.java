package com.imooc.user.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.imooc.thrift.user.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public abstract class LoginFilter implements Filter {

    private static Cache<String,UserDTO> cache=
            CacheBuilder.newBuilder().maximumSize(10000).
            expireAfterWrite(3,TimeUnit.MINUTES).build();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq=(HttpServletRequest) request;
        HttpServletResponse rp=(HttpServletResponse) response;
        String token=rq.getParameter("token");
        if(StringUtils.isBlank(token)){
            Cookie[] cookies= rq.getCookies();
            if(cookies!=null){
                for (Cookie c:cookies){
                    if (c.getName().equals("token")){
                        token=c.getValue();
                        break;
                    }
                }
            }
        }
        UserDTO userDTO=null;
        if(StringUtils.isNotBlank(token)) {
            userDTO=cache.getIfPresent(token);
            if (userDTO==null){
                userDTO=requestUserInfo(token);
                if (userDTO!=null){
                    cache.put(token,userDTO);
                }
            }
        }

        if(userDTO==null){
            rp.sendRedirect("http://www.ma.com/user/login");
            return;
        }

        login(rq,rp,userDTO);
        chain.doFilter(rq,rp);
    }
    protected abstract String userEdgeServiceAddr();

    protected abstract void login(HttpServletRequest rq, HttpServletResponse rp, UserDTO userDTO);

    private UserDTO requestUserInfo(String token) {
        String url="http://"+userEdgeServiceAddr()+"/user/authentication";
        HttpClient client=new DefaultHttpClient();
        HttpPost post=new HttpPost(url);
        post.addHeader("token",token);
        InputStream inputStream=null;
        try{
            HttpResponse rp=client.execute(post);
            if(rp.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                throw new RuntimeException("request user info Failed! StatusLing:" +rp.getStatusLine());
            }
            inputStream=rp.getEntity().getContent();
            byte[] temp=new byte[1024];
            StringBuilder sb=new StringBuilder();
            int len=0;
            while ((len=inputStream.read(temp))>0){
                sb.append(new String(temp,0,len));
            }
            UserDTO userDTO=new ObjectMapper().readValue(sb.toString(),UserDTO.class);
            return  userDTO;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try{
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void destroy() {

    }
}
