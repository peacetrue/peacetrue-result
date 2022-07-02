package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.lang.UncheckedException;
import javassist.*;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

/**
 * @author peace
 **/
public class ResultErrorAttributesUtils {

    private ResultErrorAttributesUtils() {
    }

    /**
     * 扩展 {@link DefaultErrorAttributes} 以支持响应结果。
     * <p>
     * 必须在 {@link DefaultErrorAttributes} 被加载之前执行本方法。
     * <p>
     * * Spring Boot 1 和 Spring Boot 2 中，{@link ErrorAttributes} 所在包不同，需要区别处理。
     * * {@link ResultErrorAttributes} 只支持 Spring Boot 2，不支持 Spring Boot 1。
     *
     * @return 扩展的 {@link DefaultErrorAttributes}
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends ErrorAttributes> extendDefaultErrorAttributes() {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass clazz = pool.get("org.springframework.boot.web.servlet.error.DefaultErrorAttributes");
            CtMethod method = clazz.getDeclaredMethod("getErrorAttributes");
            method.insertBefore("{" + getInsertCode() + "}");
            return clazz.toClass(ErrorAttributes.class.getClassLoader(), ErrorAttributes.class.getProtectionDomain());
        } catch (NotFoundException | CannotCompileException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * 改写字节码时插入的代码。
     *
     * <pre>
     * public Map&le;String, Object&ge; getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
     *     Integer status = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
     *     if (Objects.equals(status, HttpStatus.NOT_FOUND.value())) return ResultErrorAttributes.handleResourceNotFound(webRequest);
     *     return super.getErrorAttributes(webRequest, includeStackTrace);
     * }
     * </pre>
     *
     * @return 代码片段
     * @see <a href="https://www.javassist.org/tutorial/tutorial2.html">javassist-tutorial2</a>
     */
    private static String getInsertCode() {
        String className = ResultErrorAttributes.class.getName();
        return "Integer status = (Integer) $1.getAttribute(\"javax.servlet.error.status_code\", 0);\n" +
                "if (status!=null&&status.intValue()==404) return " + className + ".DEFAULT.handleResourceNotFound(webRequest);";
    }
}
