package edu.columbia.dbmi.doc2hpo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.VersionDoc2Hpo;


@CrossOrigin
@Controller
public class VersionController {

	@RequestMapping("/version")
	@ResponseBody
	public VersionDoc2Hpo getVersion() {
		VersionDoc2Hpo version = new VersionDoc2Hpo();
		// major version: new function backward incompatible;
		// minor version: new function backward compatible;
		// patch: bug corrected;
		version.setDoc2hpo("1.21.0");
		version.setJava("1.8.0_191");
		version.setTomcat("8.5.35");
		version.setMetamap("2016v2");
		version.setMetamaplite("metamaplite-3.6.2rc3.jar");
		version.setNcbo(null);
		return version;
    }

}
