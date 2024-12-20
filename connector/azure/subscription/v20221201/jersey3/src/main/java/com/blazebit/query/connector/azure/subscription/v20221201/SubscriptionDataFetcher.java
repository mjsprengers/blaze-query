/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */
package com.blazebit.query.connector.azure.subscription.v20221201;

import com.blazebit.query.connector.azure.base.invoker.ApiClient;
import com.blazebit.query.spi.DataFetcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.blazebit.query.connector.azure.base.AzureConnectorConfig;
import com.blazebit.query.connector.azure.base.invoker.ApiException;
import com.blazebit.query.connector.azure.subscription.v20221201.api.SubscriptionsApi;
import com.blazebit.query.connector.azure.subscription.v20221201.model.Subscription;
import com.blazebit.query.connector.base.DataFormats;
import com.blazebit.query.spi.DataFetcher;
import com.blazebit.query.spi.DataFetchContext;
import com.blazebit.query.spi.DataFormat;

/**
 * @author Christian Beikov
 * @since 1.0.0
 */
public class SubscriptionDataFetcher implements DataFetcher<Subscription>, Serializable {

	public static final SubscriptionDataFetcher INSTANCE = new SubscriptionDataFetcher();

	private SubscriptionDataFetcher() {
	}

	@Override
	public List<Subscription> fetch(DataFetchContext context) {
		try {
			List<ApiClient> apiClients = AzureConnectorConfig.API_CLIENT.getAll( context );
			List<Subscription> list = new ArrayList<>();
			for ( ApiClient apiClient : apiClients ) {
				list.addAll( new SubscriptionsApi( apiClient ).subscriptionsList( "2022-12-01" ).getValue() );
			}
			return list;
		}
		catch (ApiException e) {
			throw new DataFetcherException( "Could not fetch subscription list", e );
		}
	}

	@Override
	public DataFormat getDataFormat() {
		return DataFormats.beansConvention( Subscription.class );
	}
}
