/*

    Copyright 2018-2021 Accenture Technology

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */

package org.platformlambda.core.annotations;

import java.lang.annotation.*;

/**
 * This indicates that the annotated class is optional.
 * <p>
 * If a parameter name is used as value, the condition is evaluated as "parameter=true".
 * <p>
 * Otherwise, simple condition is supported. e.g.
 * <p>
 * !hello.world - the module will be enabled if hello.world is false
 * <p>
 * hello.world=12345 - the module will be enabled if hello.world is 12345
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalService {

    String value();
}
