package com.thonecardoso.bookstoremanager.users.utils;

import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;

public class MessageDTOUtils {
    public static MessageDTO creationMessage(String createdUserName, Long createdUserId) {
        return returnMessageDTO(createdUserName, createdUserId, "created");
    }

    public static MessageDTO updatedMessage(String createdUserName, Long createdUserId) {
        return returnMessageDTO(createdUserName, createdUserId, "updated");
    }

    private static MessageDTO returnMessageDTO(String createdUserName, Long createdUserId, String action) {
        String createdUserMessage = String
                .format("User %s with ID %s successfully %s", createdUserName, createdUserId, action);

        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }

}
