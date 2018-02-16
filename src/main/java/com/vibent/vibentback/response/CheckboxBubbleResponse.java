package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.usersAnswers.UsersCheckboxAnswers;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CheckboxBubbleResponse {
    private CheckboxBubble checkboxBubble;
    private ArrayList<CheckboxAnswer> checkboxAnswers;
    private ArrayList<UsersCheckboxAnswers> usersCheckboxAnswers;
}
