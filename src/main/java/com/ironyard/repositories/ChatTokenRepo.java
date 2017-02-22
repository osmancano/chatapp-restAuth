package com.ironyard.repositories;

import com.ironyard.data.ChatToken;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by wailm.yousif on 2/21/17.
 */
public interface ChatTokenRepo extends PagingAndSortingRepository<ChatToken, Long> {
    public ChatToken findByToken(String token);
}