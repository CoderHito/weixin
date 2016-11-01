package com.cf.framework.utils.method;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class doFilterMethod implements TemplateMethodModelEx {

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		for(int i=0;i<arg0.size();i++){
			System.out.println(arg0.get(i));
		}
		return arg0.get(1);
	}


}
