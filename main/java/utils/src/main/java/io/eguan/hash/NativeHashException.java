package io.eguan.hash;

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

/**
 * Exception thrown when a native implementation of a hash implementation has failed.
 * 
 * @author oodrive
 * @author llambert
 * @author ebredzinski
 * 
 */
public final class NativeHashException extends RuntimeException {

    /** Not serialized: dummy serial number. */
    private static final long serialVersionUID = 1L;

    public NativeHashException() {
        super();
    }

    public NativeHashException(final String message) {
        super(message);
    }

    public NativeHashException(final Throwable cause) {
        super(cause);
    }

    public NativeHashException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NativeHashException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
