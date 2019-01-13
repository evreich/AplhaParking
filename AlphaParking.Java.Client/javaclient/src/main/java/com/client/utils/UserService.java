package com.client.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.client.models.User;
import com.client.response_jsons.UsersResponse;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    HttpClient httpClient;
    @Autowired
    HttpServletRequest httpRequest;
 
    private List<User> users; 

    public UserService (List<User> users)  {
        this.users = users;
    }
    
    public Page<User> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
 
        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }
 
        Page<User> userPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), users.size());
 
        return userPage;
    }
}