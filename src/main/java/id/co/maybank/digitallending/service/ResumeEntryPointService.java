package id.co.maybank.digitallending.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.maybank.digitallending.common.BusinessTypeConst;
import id.co.maybank.digitallending.model.StepTracker;
import id.co.maybank.digitallending.repository.StepTrackerRepository;
import id.co.maybank.digitallending.dto.ResumeResponse;
import id.co.maybank.digitallending.validator.BusinessTypeValidator;
import id.co.maybank.digitallending.validator.IdCardValidator;
import id.co.maybank.digitallending.validator.TaxPayerValidator;
import id.co.maybank.digitallending.validator.ValidationException;

@Service
public class ResumeEntryPointService {

	@Autowired
	private IdCardValidator idCardValidator;

	@Autowired
	private BusinessTypeValidator businessTypeValidator;

	@Autowired
	private TaxPayerValidator taxPayerValidator;

	@Autowired
	private StepTrackerRepository stepTrackRepo;

	public ResumeResponse inquiry(String idCardNumber, String businessType, String taxPayerNumber)
			throws ValidationException {

		var res = new ResumeResponse();

		this.businessTypeValidator.validate(businessType);

		this.idCardValidator.validate(idCardNumber);

		if (BusinessTypeConst.CORPORATE.equals(businessType)) {
			this.taxPayerValidator.validate(taxPayerNumber);

		}

		List<StepTracker> trackers;
		if (BusinessTypeConst.CORPORATE.equals(businessType)) {
			trackers = this.stepTrackRepo.findByLinkedId(taxPayerNumber);
		} else {
			trackers = this.stepTrackRepo.findByLinkedId(idCardNumber);
		}

		if (CollectionUtils.isNotEmpty(trackers)) {
			var stepComplete = trackers.get(0).getStepCompleted();
			res.setStepComplete(stepComplete);
		}

		return res;
	}

}
