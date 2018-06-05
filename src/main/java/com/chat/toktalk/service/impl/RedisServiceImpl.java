package com.chat.toktalk.service.impl;

import com.chat.toktalk.domain.Channel;
import com.chat.toktalk.domain.User;
import com.chat.toktalk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    List<User> userList = new ArrayList<>();
    List<Channel> channelList = new ArrayList<>();

//    @Override
//    public void addUser(Long id, WebSocketSession session) {
//        redisTemplate.opsForList().rightPush(id,session.getId());
//    }

    @Override
    public void addUser(Long id, String userId) {
        redisTemplate.opsForList().rightPush(id,userId);
    }

    @Override
    public void addChannel(String userId, Long roomNo) {
        redisTemplate.opsForList().rightPush(userId,roomNo);
    }

    @Override
    public List<User> getUsers(Long id) {
        // 해당 채널 키로 레디스의 모든 유저 가져오기. 인데스 0부터 -1은 있는거 다 불러오는거
        userList = redisTemplate.opsForList().range(id,0,-1);
        return userList;
    }

    @Override
    public User getUser(Principal principal) {
        User user = new User();
        for (int i=0;i<redisTemplate.opsForList().size("roonNo");i++) {
            user = (User)redisTemplate.opsForList().leftPop("roonNo");
            if(!principal.getName().equals(user.getEmail())) {
                continue;
            }
            break;
        }
        return user;
    }

    @Override
    public List<Channel> getChannels(String userId) {
        channelList = redisTemplate.opsForList().range(userId,0,-1);
        return channelList;
    }
}