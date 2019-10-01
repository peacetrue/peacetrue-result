class Result {

    /**
     * 标准响应数据
     * @param {string|number} code 状态编码
     * @param {string} message 状态描述
     * @param {*} [data] 负载数据
     */
    constructor(code, message, data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

module.exports = Result;