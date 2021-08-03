package com.starter.service.mapper;

import com.starter.config.Constants;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {})
public interface InstantOffsetDateTimeMapper {

  default OffsetDateTime map(Instant time) {
    return time == null
        ? null
        : OffsetDateTime.ofInstant(time, ZoneId.of(Constants.TIMEZONE));
  }

  default Instant map(OffsetDateTime time) {
    return time == null
        ? null
        : time.withOffsetSameInstant(ZoneOffset.ofHours(Constants.TIMEZONE_HOURS))
            .toInstant();
  }
}
