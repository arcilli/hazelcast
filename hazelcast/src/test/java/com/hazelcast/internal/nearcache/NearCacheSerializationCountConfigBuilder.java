/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.nearcache;

import java.util.List;

import static java.lang.String.format;

public class NearCacheSerializationCountConfigBuilder {

    private static final String DELIMITER = ", ";

    private final StringBuilder sb = new StringBuilder();

    private String baseString;

    public void append(int[] intArray) {
        String delimiter = "";
        sb.append("newInt(");
        for (int count : intArray) {
            sb.append(delimiter).append(count);
            delimiter = DELIMITER;
        }
        sb.append(")").append(DELIMITER);
    }

    public void append(Object option) {
        sb.append(option).append(DELIMITER);
    }

    public String build(boolean isKey, boolean isSerialization, int index, List<String> stacktrace) {
        if (baseString == null) {
            baseString = sb.substring(0, sb.length() - DELIMITER.length());
        }
        return format("%s%n%s%n%s", baseString, createPointer(isKey, isSerialization, index), stacktrace);
    }

    private static String createPointer(boolean isKey, boolean isSerialization, int index) {
        int arrayWidth = 17;
        int offset = 7;
        if (!isKey) {
            offset += 2 * arrayWidth;
        }
        if (!isSerialization) {
            offset += arrayWidth;
        }
        offset += index * 3;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < offset; i++) {
            sb.append(" ");
        }
        sb.append("↑");
        return sb.toString();
    }
}
