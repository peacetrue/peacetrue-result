package com.github.peacetrue.result;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author peace
 **/
class ResultUtilsTest {

    private final static EasyRandom EASY_RANDOM = new EasyRandom();

    @Test
    void testToString() {
        Result result = EASY_RANDOM.nextObject(ResultImpl.class);
        Result dataResult = EASY_RANDOM.nextObject(GenericDataResult.class);
        Assertions.assertEquals(result.toString(), ResultUtils.build(result).toString());
        Assertions.assertEquals(dataResult.toString(), ResultUtils.build(dataResult).toString());

        dataResult = ResultUtils.setData(dataResult, null);
        Assertions.assertEquals(dataResult.toString(), ResultUtils.build(dataResult).toString());
        dataResult = ResultUtils.setData(dataResult, new String[]{"dd"});
        Assertions.assertEquals(dataResult.toString(), ResultUtils.build(dataResult).toString());

        Assertions.assertTrue(new DataResultImpl<>(result, null).toString().contains("<null>"));
    }

    @Test
    void build() {
        Assertions.assertFalse(ResultUtils.build("", "", null) instanceof DataResult);
        Assertions.assertTrue(ResultUtils.build("", "", "") instanceof DataResult);

        Result result = ResultUtils.build(ResultTypes.SUCCESS, null);
        Assertions.assertEquals(ResultTypes.SUCCESS.getCode(), result.getCode());
        Result dataResult = ResultUtils.build(ResultTypes.SUCCESS, "");
        Assertions.assertTrue(dataResult instanceof DataResult);

        Assertions.assertEquals(result, ResultUtils.build(result));
        Assertions.assertEquals(dataResult, ResultUtils.build(dataResult));
    }

    @Test
    void setCode() {
        Result result = EASY_RANDOM.nextObject(ResultImpl.class);
        Result dataResult = EASY_RANDOM.nextObject(GenericDataResult.class);

        result = ResultUtils.setCode(result, "");
        Assertions.assertEquals("", result.getCode());

        dataResult = ResultUtils.setCode(dataResult, "");
        Assertions.assertEquals("", dataResult.getCode());

        result = Mockito.mock(Result.class);
        dataResult = Mockito.mock(DataResult.class);

        result = ResultUtils.setCode(result, "");
        Assertions.assertEquals("", result.getCode());

        dataResult = ResultUtils.setCode(dataResult, "");
        Assertions.assertEquals("", dataResult.getCode());
    }

    @Test
    void setData() {
        Result result = EASY_RANDOM.nextObject(ResultImpl.class);
        Result dataResult = EASY_RANDOM.nextObject(GenericDataResult.class);

        Assertions.assertFalse(ResultUtils.setData(result, null) instanceof DataResult);
        Assertions.assertTrue(ResultUtils.setData(result, "") instanceof DataResult);
        Assertions.assertFalse(ResultUtils.setData(dataResult, null) instanceof DataResult);
        Assertions.assertTrue(ResultUtils.setData(dataResult, "") instanceof DataResult);

        result = Mockito.mock(Result.class);
        Assertions.assertTrue(ResultUtils.setData(result, "") instanceof DataResult);
    }


    @Test
    void getData() {
        Assertions.assertNull(ResultUtils.getData(new ResultImpl()));
        Assertions.assertNotNull(ResultUtils.getData(new DataResultImpl<>("", "", "")));
    }


}
