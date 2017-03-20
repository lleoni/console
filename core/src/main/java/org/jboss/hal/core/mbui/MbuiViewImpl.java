/*
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
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
package org.jboss.hal.core.mbui;

import java.util.Map;

import org.jboss.hal.ballroom.form.Form;
import org.jboss.hal.core.mvp.HalViewImpl;
import org.jboss.hal.dmr.model.ResourceAddress;
import org.jboss.hal.meta.AddressTemplate;
import org.jboss.hal.meta.Metadata;

/**
 * Base class for views generated using {@code @MbuiView}.
 *
 * @author Harald Pehl
 */
public abstract class MbuiViewImpl<P extends MbuiPresenter> extends HalViewImpl implements MbuiView<P> {

    protected final MbuiContext mbuiContext;
    protected P presenter;

    protected MbuiViewImpl(final MbuiContext mbuiContext) {
        this.mbuiContext = mbuiContext;
    }

    @Override
    public void setPresenter(final P presenter) {
        this.presenter = presenter;
    }

    protected void add(final String id, final String type, final AddressTemplate template) {
        mbuiContext.crud().add(id, type, template, (name, address) -> presenter.reload());
    }

    protected void addSingleton(final String id, final String type, final AddressTemplate template) {
        mbuiContext.crud().addSingleton(id, type, template, (name, address) -> presenter.reload());
    }

    protected void saveForm(final String type, final String name, final ResourceAddress address,
            final Map<String, Object> changedValues, final Metadata metadata) {
        mbuiContext.crud().save(type, name, address, changedValues, metadata, () -> presenter.reload());
    }

    protected void saveSingletonForm(final String type, final ResourceAddress address,
            final Map<String, Object> changedValues, final Metadata metadata) {
        mbuiContext.crud().saveSingleton(type, address, changedValues, metadata, () -> presenter.reload());
    }

    protected <T> void resetForm(final String type, final String name, final ResourceAddress address,
            final Form<T> form, final Metadata metadata) {
        mbuiContext.crud().reset(type, name, address, form, metadata, () -> presenter.reload());
    }

    protected <T> void resetSingletonForm(final String type, final ResourceAddress address,
            final Form<T> form, final Metadata metadata) {
        mbuiContext.crud().resetSingleton(type, address, form, metadata, () -> presenter.reload());
    }
}
