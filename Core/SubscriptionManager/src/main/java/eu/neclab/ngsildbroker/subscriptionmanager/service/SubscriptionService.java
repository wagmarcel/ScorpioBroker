package eu.neclab.ngsildbroker.subscriptionmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import eu.neclab.ngsildbroker.commons.datatypes.BaseRequest;
import eu.neclab.ngsildbroker.commons.subscriptionbase.BaseSubscriptionService;
import eu.neclab.ngsildbroker.commons.subscriptionbase.SubscriptionInfoDAOInterface;
import eu.neclab.ngsildbroker.commons.tools.EntityTools;

@Service
public class SubscriptionService extends BaseSubscriptionService {
	@Autowired
	@Qualifier("subdao")
	SubscriptionInfoDAOInterface subService;
	
	@Override
	protected SubscriptionInfoDAOInterface getSubscriptionInfoDao() {
		return subService;
	}

	@Override
	protected Set<String> getTypesFromEntry(BaseRequest createRequest) {
		return EntityTools.getTypesFromEntity(createRequest);
	}
}
