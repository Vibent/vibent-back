package com.vibent.vibentback.api.bubble.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class SurveyAnswerReq {

    @NonNull
    private Long bubbleId;
    @NonNull
    private String creatorRef;
    @NonNull
    private String content;
}
