package com.ironyard.controllers.rest;

import com.ironyard.data.ChatBoard;
import com.ironyard.data.ChatMessage;
import com.ironyard.data.ChatToken;
import com.ironyard.data.ChatUser;
import com.ironyard.repositories.ChatMessageRepo;
import com.ironyard.repositories.ChatTokenRepo;
import com.ironyard.repositories.ChatUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by osmanidris on 2/20/17.
 */
@RestController
public class RestMessageController {
    @Autowired
    ChatMessageRepo chatMessageRepo;
    @Autowired
    ChatUserRepo chatUserRepo;
    @Autowired
    ChatTokenRepo chatTokenRepo;

    @RequestMapping(path = "/rest/chatapp/messages", method = RequestMethod.GET)
    public Page<ChatMessage> listMessages(@RequestAttribute String authToken,@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size,
                              @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 2;
        }
        if (sortBy == null) {
            sortBy = "postedDate";
        }
        Sort s = new Sort(Sort.Direction.DESC, sortBy);
        PageRequest pr = new PageRequest(page, size, s);
        Page<ChatMessage> messages = chatMessageRepo.findAll(pr);
        this.increaseNumOfCalls(authToken);
        return messages;
    }

    @RequestMapping(path = "/rest/chatapp/messages/create", method = RequestMethod.POST)
    public ChatMessage createMessage(@RequestAttribute String authToken,@RequestAttribute Long userId, @RequestBody ChatMessage saveToDB) throws Exception {
        //Get the Msg User from the User ID provide in the request
        ChatUser msgUser = chatUserRepo.findOne(userId);
        saveToDB.setMsgUser(msgUser);
        // save to database
        chatMessageRepo.save(saveToDB);
        this.increaseNumOfCalls(authToken);
        return saveToDB;
    }

    @RequestMapping(path = "/rest/chatapp/messages/getOne/{id}",  method = RequestMethod.GET)
    public ChatMessage findByMovieId(@RequestAttribute String authToken, @PathVariable Long id) throws Exception {
        // save to database
        ChatMessage found = chatMessageRepo.findOne(id);
        this.increaseNumOfCalls(authToken);
        return found;
    }

    @RequestMapping(path = "/rest/chatapp/messages/update",  method = RequestMethod.PATCH)
    public ChatMessage editMovie(@RequestAttribute String authToken, @RequestBody ChatMessage saveToDB) throws Exception {
        // save to database
        if(saveToDB.getId() == 0){
            throw new Exception("Patch update must specify message id.");
        }
        chatMessageRepo.save(saveToDB);
        this.increaseNumOfCalls(authToken);
        return saveToDB;
    }

    public void increaseNumOfCalls(String token){
        try {
            ChatToken chatToken = chatTokenRepo.findByToken(token);
            chatToken.setNumOfCalls(chatToken.getNumOfCalls() + 1);
            chatTokenRepo.save(chatToken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
