package io.eguan.vold.model;

/*
 * #%L
 * Project eguan
 * %%
 * Copyright (C) 2012 - 2016 Oodrive
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.eguan.hash.HashAlgorithm;
import io.eguan.nbdsrv.client.NbdClientBasicIops;
import io.eguan.vold.model.VoldTestHelper.CompressionType;

public class TestVoldBasicIopsOnTargetNbd extends TestVoldBasicIopsOnTargetAbstract {

    public TestVoldBasicIopsOnTargetNbd(final CompressionType compressionType, final HashAlgorithm hash,
            final Integer blockSize, final Integer numBlocks) throws Exception {
        super(compressionType, hash, blockSize, numBlocks);
    }

    @Override
    public NbdClientBasicIops initClient() {
        return new NbdClientBasicIops(10809);
    }
}
