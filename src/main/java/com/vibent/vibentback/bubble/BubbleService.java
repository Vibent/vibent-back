package com.vibent.vibentback.bubble;

import com.vibent.vibentback.bubble.alimentation.AlimentationService;
import com.vibent.vibentback.bubble.checkbox.CheckboxService;
import com.vibent.vibentback.bubble.free.FreeService;
import com.vibent.vibentback.bubble.location.LocationService;
import com.vibent.vibentback.bubble.planning.PlanningService;
import com.vibent.vibentback.bubble.survey.SurveyService;
import com.vibent.vibentback.bubble.travel.TravelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bubble", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BubbleService {

    AlimentationService alimentationService;
    CheckboxService checkboxService;
    FreeService freeService;
    LocationService locationService;
    PlanningService planningService;
    SurveyService surveyService;
    TravelService travelService;

    void deleteBubble(@PathVariable Long id) {
        alimentationService.deleteBubble(id);
        checkboxService.deleteBubble(id);
        freeService.deleteBubble(id);
        locationService.deleteBubble(id);
        planningService.deleteBubble(id);
        surveyService.deleteBubble(id);
        travelService.deleteBubble(id);
    }
}
