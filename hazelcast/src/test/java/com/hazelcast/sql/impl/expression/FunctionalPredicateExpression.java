/*
 * Copyright (c) 2008-2023, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.sql.impl.expression;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.sql.impl.row.Row;
import com.hazelcast.sql.impl.type.QueryDataType;

import java.io.IOException;

public class FunctionalPredicateExpression implements Expression<Boolean> {

    private NullablePredicate predicate;

    public FunctionalPredicateExpression(NullablePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public Boolean eval(Row row, ExpressionEvalContext context) {
        return predicate.test(row);
    }

    @Override
    public QueryDataType getType() {
        return QueryDataType.BOOLEAN;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeObject(predicate);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        predicate = in.readObject();
    }

    @FunctionalInterface
    public interface NullablePredicate {
        Boolean test(Row row);
    }
}
