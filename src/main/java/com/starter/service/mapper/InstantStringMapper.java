package com.starter.service.mapper;

import com.starter.config.Constants;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {})
public interface InstantStringMapper {

  default String map(Instant time) {
    return time == null
        ? null
        : DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            .withZone(ZoneId.of(Constants.TIMEZONE)).format(time);
  }

  default Instant map(String time) {
    return time == null
        ? null
        : Instant.parse(
            LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneId.of(Constants.TIMEZONE))).atZone(ZoneId.of(Constants.TIMEZONE))
                .toInstant()
                .toString());

  }
}
