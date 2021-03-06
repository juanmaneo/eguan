package io.eguan.nbdsrv.packet;

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

public final class NbdException extends Exception {

    /** The Serial Version ID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with null as its detail message.
     */
    public NbdException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param msg
     *            the detail message. The detail message is saved for later retrieval by the Throwable.getMessage()
     *            method.
     */
    public NbdException(final String msg) {
        super(msg);
    }

    /**
     * Constructs a new exception with the specified exception.
     * 
     * @param e
     *            the Exception
     */
    public NbdException(final Exception e) {
        super(e);
    }
}
