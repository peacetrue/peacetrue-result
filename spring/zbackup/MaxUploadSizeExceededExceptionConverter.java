//package com.github.peacetrue.result.exception.spring;
//
//import com.github.peacetrue.result.exception.AbstractExceptionConverter;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//
///**
// * 达到上传允许的最大空间
// *
// * @author peace
// */
//public class MaxUploadSizeExceededExceptionConverter
//        extends AbstractExceptionConverter<MaxUploadSizeExceededException> {
//
//    public static String humanReadableByteCount(long bytes) {
//        return humanReadableByteCount(bytes, true);
//    }
//
//    public static String humanReadableByteCount(long bytes, boolean si) {
//        int unit = si ? 1000 : 1024;
//        if (bytes < unit) return bytes + " B";
//        int exp = (int) (Math.log(bytes) / Math.log(unit));
//        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
//        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
//    }
//
//    @Override
//    protected Object[] resolveArgs(MaxUploadSizeExceededException exception) {
//        return new Object[]{humanReadableByteCount(exception.getMaxUploadSize())};
//    }
//
//}
