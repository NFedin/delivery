package com.nikfedin.delivery.services;

import com.github.agogs.holidayapi.api.APIConsumer;
import com.github.agogs.holidayapi.api.impl.HolidayAPIConsumer;
import com.github.agogs.holidayapi.model.Holiday;
import com.github.agogs.holidayapi.model.HolidayAPIResponse;
import com.github.agogs.holidayapi.model.QueryParams;
import com.nikfedin.delivery.models.Timeslot;
import com.nikfedin.delivery.repositories.TimeslotRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
//@AllArgsConstructor
public class TimeslotService {
    @Value("${holiday.api.key}")
    private String holidayApiKey;

    private TimeslotRepository timeslotRepository;

    @Autowired
    public TimeslotService(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    public void initializeTimeslots() throws FileNotFoundException, ParseException {
        JSONParser parser = new JSONParser(new FileReader("courierAPI.json"));
        List<Map<String, Object>> list = (List<Map<String, Object>>) parser.parse();
        List<Timeslot> timeslots = list.stream().map(l ->
                Timeslot.builder()
                        .startDate(LocalDateTime.parse(l.get("startDate").toString()))
                        .endDate(LocalDateTime.parse(l.get("endDate").toString()))
                        .supportedPostcodes((ArrayList<String>)l.get("postcodes"))
                        .build()).toList();
        timeslots.stream().filter(holiday -> !checkIsHoliday(holiday)).forEach(timeslot -> timeslotRepository.saveAndFlush(timeslot));
    }

    private boolean checkIsHoliday(Timeslot timeslot) {
        LocalDate startDateOfTimeslot = timeslot.getStartDate().toLocalDate();
        APIConsumer consumer = new HolidayAPIConsumer("https://holidayapi.com/v1/holidays");
        QueryParams params = new QueryParams();
        params.key(holidayApiKey)
                .month(startDateOfTimeslot.getMonthValue())
                .country(QueryParams.Country.ISRAEL)
                .year(startDateOfTimeslot.getYear())
                //JSON is the default format
                .format(QueryParams.Format.JSON)
                .pretty(true);
        try {
            HolidayAPIResponse response = consumer.getHolidays(params);
            int status = response.getStatus();
            if (status != 200) {
                log.info("Failed validation for timeslot with status " + status);
                return false;
            } else {
                List<Holiday> holidays = response.getHolidays();
                boolean result = holidays.stream().anyMatch(holiday -> LocalDate.parse(holiday.getDate()).equals(startDateOfTimeslot));
                if (result) {
                    log.info("This timeslot fall on holidays");
                }
                return result;
            }
        } catch (IOException e) {
            log.error("Failed validation for timeslot");
            return false;
        }
    }

    public List<Timeslot> getAllAvailableTimeslots(String postcode) {
        return timeslotRepository.findAll().stream()
                .filter(timeslot -> timeslot.getSupportedPostcodes().contains(postcode))
                .collect(Collectors.toList());
    }
}
