const assert = require('assert');
const axios = require('axios').create();
require('../src/client/url_not_exists').inject(axios);

//此测试需要先运行：NormalResultApplication

describe('#client/url_not_exists.js', () => {

    describe('#onFulfilled', () => {
        it('访问 http://localhost:8081/calculate/divide?divisor=10&dividend=10 应该正常', () => {
            return axios.get('http://localhost:8081/calculate/divide?divisor=10&dividend=10')
                .then(wrapper => {assert.strictEqual(1, wrapper.data);})
                .catch(wrapper => {assert.fail('应该是正常状态');});
        });
    });

    describe('#onRejected', () => {
        it('访问 http://localhost:8081/calculate 应该404', () => {
            return axios.get('http://localhost:8081/calculate')
                .then(wrapper => {assert.fail();})
                .catch(wrapper => {
                    assert.strictEqual('url_not_exists', wrapper.data.code);
                });
        });
    });
});


