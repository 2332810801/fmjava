package com.fmjava.core.service;
/**
 * 自定义的认证类
 */

import com.fmjava.core.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserDetailServiceImpl implements UserDetailsService {


    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* 定义权限的集合*/
        ArrayList<GrantedAuthority> authList=new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        if(username==null){//如果没有输入用户名 直接结束返回
            return null;
        }
        //到数据中查询用户对象
        Seller seller = sellerService.findOne(username);
        if(seller!=null){
            if("1".equals(seller.getStatus())){//如果状态是审核通过的商家 Status==1
              return new User(username,seller.getPassword(),authList);//如果数据库中有这个用户 创建User权限对象 把用户名密码，权限集合放进去
            }
        }
        return null;
    }
}
