package com.aws.codestar.usersmanagement.handler;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Attribute;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.usersmanagement.GatewayResponse;
import com.aws.codestar.usersmanagement.dataccess.UserOperator;
import com.aws.codestar.usersmanagement.models.RegistrationRequest;
import com.aws.codestar.usersmanagement.models.TerminationRequest;
import com.aws.codestar.usersmanagement.models.UserOperationResponse;

public class UserTerminationHandler implements RequestHandler<TerminationRequest, GatewayResponse> {

	@Override
	public GatewayResponse handleRequest(TerminationRequest arg0, Context arg1) {
		// TODO Auto-generated method stub
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		UserOperationResponse res = getTerminationResponse(arg0);
		return new GatewayResponse(res.toString(), headers, 200);
	}

	private UserOperationResponse getTerminationResponse(TerminationRequest input) {
		UserOperator terminator = new UserOperator();
		int creationCode = terminator.deleteUser(input);
		UserOperationResponse response = new UserOperationResponse();
		response.setResponseCode(creationCode);
		response.setResponseText("SUCCESS");
		return response;
	}

	
}
