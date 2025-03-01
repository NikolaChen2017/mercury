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

package com.accenture.demo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.platformlambda.core.exception.AppException;
import org.platformlambda.core.models.EventEnvelope;
import org.platformlambda.core.models.Kv;
import org.platformlambda.core.system.Platform;
import org.platformlambda.core.system.PostOffice;
import com.accenture.examples.services.DemoMath;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;

public class DemoMathTest {

    @BeforeClass
    public static void setup() throws IOException {
        Platform.getInstance().register("math.addition", new DemoMath(), 5);
    }

    @Test
    public void demo() throws IOException, TimeoutException, AppException {

        int a = 100;
        int b = 200;

        PostOffice po = PostOffice.getInstance();
        EventEnvelope response = po.request("math.addition", 5000, new Kv("a", a), new Kv("b", b));
        Assert.assertFalse(response.hasError());
        Assert.assertEquals(100 + 200, response.getBody());

    }


}
