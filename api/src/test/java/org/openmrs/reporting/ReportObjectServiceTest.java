/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.reporting;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.PatientSetService;
import org.openmrs.api.context.Context;
import org.openmrs.cohort.CohortSearchHistory;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;

public class ReportObjectServiceTest extends BaseContextSensitiveTest {
	
	/**
	 * @see {@link ReportObjectService#saveSearchHistory(CohortSearchHistory)}
	 * 
	 */
	@Test
	@Verifies(value = "should save history successfully", method = "saveSearchHistory(CohortSearchHistory)")
	public void saveSearchHistory_shouldSaveHistorySuccessfully() throws Exception {
		// make a patient search object
		PatientSearch search = new PatientSearch();
		search.setFilterClass(ObsPatientFilter.class);
		List<SearchArgument> args = new ArrayList<SearchArgument>();
		args.add(new SearchArgument("timeModifier", "ANY", PatientSetService.TimeModifier.class));
		args.add(new SearchArgument("question", Context.getConceptService().getConceptByName("CD4 COUNT").getConceptId()
		        .toString(), Concept.class));
		args.add(new SearchArgument("withinLastDays", "1", Integer.class)); //one indicates the number of days
		search.setArguments(args);
		
		// save the object to the database
		CohortSearchHistory history = new CohortSearchHistory();
		history.setName("Some name");
		history.setDescription("a description");
		history.addSearchItem(search);
		Context.getReportObjectService().saveSearchHistory(history);
	}
}
