/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Blazebit
 */
package com.blazebit.query.connector.azure.storage.accounts.v20230501;

import com.blazebit.query.spi.DataFetcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.blazebit.query.connector.azure.base.AzureConnectorConfig;
import com.blazebit.query.connector.azure.base.invoker.ApiClient;
import com.blazebit.query.connector.azure.base.invoker.ApiException;
import com.blazebit.query.connector.azure.storage.accounts.v20230501.api.StorageAccountsApi;
import com.blazebit.query.connector.azure.storage.accounts.v20230501.model.StorageAccount;
import com.blazebit.query.connector.azure.storage.accounts.v20230501.model.StorageAccountListResult;
import com.blazebit.query.connector.azure.subscription.v20221201.model.Subscription;
import com.blazebit.query.connector.base.DataFormats;
import com.blazebit.query.spi.DataFetcher;
import com.blazebit.query.spi.DataFetchContext;
import com.blazebit.query.spi.DataFormat;

/**
 * @author Christian Beikov
 * @since 1.0.0
 */
public class StorageAccountsDataFetcher implements DataFetcher<StorageAccount>, Serializable {

	public static final StorageAccountsDataFetcher INSTANCE = new StorageAccountsDataFetcher();

	private StorageAccountsDataFetcher() {
	}

	@Override
	public List<StorageAccount> fetch(DataFetchContext context) {
		try {
			List<ApiClient> apiClients = AzureConnectorConfig.API_CLIENT.getAll( context );
			List<StorageAccount> list = new ArrayList<>();
			for ( ApiClient apiClient : apiClients ) {
				StorageAccountsApi storageAccountsApi = new StorageAccountsApi( apiClient );
				for ( Subscription subscription : context.getSession().getOrFetch( Subscription.class ) ) {
					StorageAccountListResult storageAccountListResult = storageAccountsApi.storageAccountsList(
							"2023-05-01",
							subscription.getSubscriptionId()
					);
					list.addAll( storageAccountListResult.getValue() );
				}
			}
			return list;
		}
		catch (ApiException e) {
			throw new DataFetcherException( "Could not fetch virtual machine list", e );
		}
	}

	@Override
	public DataFormat getDataFormat() {
		return DataFormats.beansConvention( StorageAccount.class );
	}
}
