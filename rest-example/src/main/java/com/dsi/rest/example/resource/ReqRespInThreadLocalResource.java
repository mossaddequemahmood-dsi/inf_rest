package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.dsi.rest.requestresponse.holder.RequestResponseHolder;

@RestResource
public class ReqRespInThreadLocalResource {

	/*
	 * Sample command - curl -v -XGET
	 * http://localhost:8080/getReqRespThreadLocal
	 */
	@Path(mapping = "getReqRespThreadLocal")
	public String getReqRespThreadLocal(Request request, Response response) {

		StringBuilder sb = new StringBuilder();
		sb.append("Req should be same -> [");
		sb.append(request);
		sb.append("<->");
		sb.append(RequestResponseHolder.getRequest());
		sb.append("]");

		sb.append("\n\n");

		sb.append("Resp should be same -> [");
		sb.append(response);
		sb.append("<->");
		sb.append(RequestResponseHolder.getResponse());
		sb.append("]");

		sb.append("\n\n");

		return sb.toString();
	}

}