package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CheckboxBubbleResponse {
    private CheckboxBubble checkboxBubble;
    private ArrayList<CheckboxBubbleResponse> checkboxBubbleResponses;
    private ArrayList<UsersCheckboxResponses> usersCheckboxResponses;
}
