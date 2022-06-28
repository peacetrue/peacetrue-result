package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import com.github.peacetrue.signature.TimestampInvalidException;
import com.github.peacetrue.util.MapUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;

/**
 * {@link TimestampInvalidException} 异常转换器。
 *
 * @author peace
 **/
public class TimestampInvalidExceptionConverter
        extends AbstractExceptionConverter<TimestampInvalidException>
        implements ClassifiedResultCode {

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_ILLEGAL.getCode();
    }

    @Nullable
    @Override
    protected Object resolveArgs(TimestampInvalidException exception) {
        ZoneId zoneId = LocaleContextHolder.getTimeZone().toZoneId();
        return MapUtils.from(
                new String[]{"clientTimestamp", "timestampRange", "serverTimestamp", "timestampOffset"},
                new Object[]{
                        toString(zoneId, exception.getClientTimestamp()),
                        exception.getTimestampOffset().increase(exception.getServerTimestamp()).toLocalDateTimeRange(zoneId).toString(),
                        toString(zoneId, exception.getServerTimestamp()),
                        exception.getTimestampOffset().toString()
                }
        );
    }

    private String toString(ZoneId zoneId, long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime().toString();
    }
}
