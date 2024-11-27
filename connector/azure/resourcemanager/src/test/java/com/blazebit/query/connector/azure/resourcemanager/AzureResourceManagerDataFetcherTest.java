/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */
package com.blazebit.query.connector.azure.resourcemanager;

import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.blazebit.query.QueryContext;
import com.blazebit.query.impl.QueryContextBuilderImpl;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureResourceManagerDataFetcherTest {

	private static final QueryContext CONTEXT;

	static {
		var builder = new QueryContextBuilderImpl();
		builder.registerSchemaProvider( new AzureResourceManagerSchemaProvider() );
		builder.registerSchemaObjectAlias( ManagedClusterInner.class, "AzureManagedCluster" );
		CONTEXT = builder.build();
	}

	@Test
	void should_return_cluster() {
		try (var session = CONTEXT.createSession()) {
			session.put(
					ManagedClusterInner.class, Collections.singletonList( AzureTestObjects.azureKubernetesService() ) );

			var typedQuery =
					session.createQuery( "select mc.* from AzureManagedCluster mc", Map.class );

			assertThat( typedQuery.getResultList() ).isNotEmpty();
		}
	}
}