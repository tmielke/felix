/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.felix.converter.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.commons.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonCodecTest {
    @Test
    public void testJSONCodec() throws Exception {
        Map<Object, Object> m1 = new HashMap<>();
        m1.put("x", true);
        m1.put("y", null);
        Map<Object, Object> m = new HashMap<>();
        m.put(1, 11L);
        m.put("ab", "cd");
        m.put(true, m1);

        JsonCodecImpl jsonCodec = new JsonCodecImpl();
        String json = jsonCodec.encode(m).toString();

        JSONObject jo = new JSONObject(json);
        assertEquals(11, jo.getInt("1"));
        assertEquals("cd", jo.getString("ab"));
        JSONObject jo2 = jo.getJSONObject("true");
        assertEquals(true, jo2.getBoolean("x"));
        assertTrue(jo2.isNull("y"));

        Map m2 = jsonCodec.decode(Map.class).from(json);
        // m2 is not exactly equal to m, as the keys are all strings now, this is unavoidable with JSON
        assertEquals(m.size(), m2.size());
        assertEquals(m.get(1), m2.get("1"));
        assertEquals(m.get("ab"), m2.get("ab"));
        assertEquals(m.get(true), m2.get("true"));
    }
}
