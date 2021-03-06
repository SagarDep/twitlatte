/*
 * Copyright 2015-2018 The twitlatte authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.moko256.twitlatte.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.moko256.twitlatte.array.ArrayUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by moko256 on 2017/06/08.
 *
 * @author moko256
 */
@RunWith(AndroidJUnit4.class)
public class CachedIdListSQLiteOpenHelperTest {

    private CachedIdListSQLiteOpenHelper helper = new CachedIdListSQLiteOpenHelper(InstrumentationRegistry.getTargetContext(), null, "testIdsDatabase");

    private List<Long> addInput = ArrayUtils.convertToLongList(0L,1L,2L);
    private List<Long> insertInput = ArrayUtils.convertToLongList(100L, 101L);

    @Test
    public void test() {
        try {
            helper.getWritableDatabase().execSQL("delete from testIdsDatabase;");
        } catch (Exception e) {
            //Do nothing
        }

        addIdTest();
        insertIdTest();
        deleteIdTest();
        setListViewPositionTest();
        helper.close();
    }


    private void addIdTest(){
        helper.addIds(addInput);

        List<Long> result1 = helper.getIds();
        for (int i = 0; i < result1.size() ; i++) {
            assertEquals(result1.get(i), addInput.get(i));
        }
    }

    private void insertIdTest(){
        helper.insertIds(1, insertInput);

        List<Long> result2 = helper.getIds();
        for (int i = 0; i < insertInput.size(); i++) {
            assertEquals(insertInput.get(i), result2.get(i + 1));
        }
    }

    private void deleteIdTest(){
        helper.deleteIds(addInput);
        helper.deleteIds(insertInput);

        assertEquals(helper.getIds().size(), 0);
    }

    private void setListViewPositionTest(){
        helper.setSeeingId(100);
        assertEquals(helper.getSeeingId(), 100);

        helper.setSeeingId(50);
        assertEquals(helper.getSeeingId(), 50);
    }
}
