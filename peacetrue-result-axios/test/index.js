/*
* 此测试需要先运行：NormalResultApplication
*/
const assert = require('assert');
const {axios, interceptors} = require('../src/index');
/*
//如果不想使用 重置综合数据拦截器，可以将其取消
interceptors.reset.inject(axios, true);
//为 标准响应数据拦截器 添加子拦截器，从第一个位置添加
interceptors.standardized.interceptors.unshift();
*/

describe('#index.js', () => {
    describe('#onFulfilled()', () => {
        it('访问 http://localhost:8081/calculate/divide?divisor=10&dividend=10 应该返回 1', () => {
            axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=10')
                .then(payload => assert.strictEqual(1, payload),
                    standardData => assert.fail());
        });
    });
    describe('#onRejected()', () => {
        it('访问 http://localhost:8081/calculate/divide 应该提示缺少参数', () => {
            axios.get('http://localhost:8081/calculate/divide')
                .then(payload => assert.fail(),
                    standardData => assert.strictEqual(400, standardData.code));
        });
    });
});