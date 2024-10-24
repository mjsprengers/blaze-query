/*
 * Copyright 2024 - 2024 Blazebit.
 *
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
 */

package com.blazebit.query.connector.github.v0314;

import java.util.Map;

import com.blazebit.query.connector.github.v0314.model.OrganizationSimple;
import com.blazebit.query.connector.github.v0314.model.Project;
import com.blazebit.query.connector.github.v0314.model.Repository;
import com.blazebit.query.connector.github.v0314.model.ShortBranch;
import com.blazebit.query.connector.github.v0314.model.Team;
import com.blazebit.query.spi.ConfigurationProvider;
import com.blazebit.query.spi.DataFetcher;
import com.blazebit.query.spi.QuerySchemaProvider;

/**
 * The schema provider for the GitHub connector.
 *
 * @author Christian Beikov
 * @since 1.0.0
 */
public final class GithubSchemaProvider implements QuerySchemaProvider {
    /**
     * Creates a new schema provider.
     */
    public GithubSchemaProvider() {
    }

    @Override
    public Map<Class<?>, ? extends DataFetcher<?>> resolveSchemaObjects(ConfigurationProvider configurationProvider) {
        return Map.<Class<?>, DataFetcher<?>>of(
                OrganizationSimple.class, OrganizationDataFetcher.INSTANCE,
                Team.class, TeamDataFetcher.INSTANCE,
                Repository.class, RepositoryDataFetcher.INSTANCE,
                ShortBranch.class, BranchDataFetcher.INSTANCE,
                Project.class, ProjectDataFetcher.INSTANCE
        );
    }
}
