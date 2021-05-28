package eu.neclab.ngsildbroker.entityhandler.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import eu.neclab.ngsildbroker.commons.enums.ErrorType;
import eu.neclab.ngsildbroker.commons.exceptions.ResponseException;
import eu.neclab.ngsildbroker.commons.storage.StorageReaderDAO;
import eu.neclab.ngsildbroker.commons.tenant.TenantAwareDataSource;



@Repository
public class EntityInfoDAO extends StorageReaderDAO {
	
	@Autowired
	TenantAwareDataSource tenantAwareDataSource;
	
	@Autowired
	private DataSource readerDataSource;
	
	public Set<String> getAllIds() {
		readerJdbcTemplate = new JdbcTemplate(readerDataSource);
		List<String> tempList = readerJdbcTemplate.queryForList("SELECT id FROM entity", String.class);
		return new HashSet<String>(tempList);
	}
	
	public Set<String> getAllTenantIds() throws ResponseException {		
		DataSource finaldatasource = tenantAwareDataSource.determineTargetDataSource();
		if(finaldatasource == null)
			throw new ResponseException(ErrorType.TenantNotFound);
		readerJdbcTemplate = new JdbcTemplate(finaldatasource);
		List<String> tempTenantList = readerJdbcTemplate.queryForList("SELECT id FROM entity", String.class);
		return new HashSet<String>(tempTenantList);
	}

	public String getEntity(String entityId) {
		readerJdbcTemplate = new JdbcTemplate(readerDataSource);
		List<String> tempList = readerJdbcTemplate.queryForList("SELECT data FROM entity WHERE id='" + entityId + "'", String.class);
		return tempList.get(0);
	}
	public String getTenantEntity(String entityId) {
		DataSource finaldatasource = tenantAwareDataSource.determineTargetDataSource();
		readerJdbcTemplate = new JdbcTemplate(finaldatasource);
		List<String> tempTenantList = readerJdbcTemplate.queryForList("SELECT data FROM entity WHERE id='" + entityId + "'", String.class);
		return tempTenantList.get(0);
	}
	
}
