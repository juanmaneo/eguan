package io.eguan.srv;

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
 * Thrown when the server configuration fails.
 * 
 * @author oodrive
 * @author llambert
 * 
 */
public class ServerConfigurationException extends RuntimeException {

    /* May be serialized by JMX */
    private static final long serialVersionUID = -3462908070285594628L;

    public ServerConfigurationException() {
        super();
    }

    public ServerConfigurationException(final String message) {
        super(message);
    }

    public ServerConfigurationException(final Throwable cause) {
        super(cause);
    }

    public ServerConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServerConfigurationException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
