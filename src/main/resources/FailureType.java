/**
 * the type of failure
 *
 * @author xiayx
 * @see ResultType
 */
public enum FailureType {

    /** 多项错误 */
    errors,
    /** 参数错误 */
    parameter_error,
    /** 参数缺失 */
    parameter_missing,
    /** 参数类型不匹配 */
    parameter_type_mismatch,
    /** 参数格式不匹配 */
    parameter_format_mismatch,
    /** 服务端错误 */
    server_error
}
