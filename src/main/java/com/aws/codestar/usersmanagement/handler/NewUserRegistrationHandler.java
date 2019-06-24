package com.aws.codestar.usersmanagement.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.usersmanagement.GatewayResponse;
import com.aws.codestar.usersmanagement.dataccess.UserOperator;
import com.aws.codestar.usersmanagement.models.RegistrationRequest;
import com.aws.codestar.usersmanagement.models.UserOperationResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class NewUserRegistrationHandler implements RequestHandler<RegistrationRequest, GatewayResponse> {

	public NewUserRegistrationHandler() {
	}

	public GatewayResponse handleRequest(final RegistrationRequest input, final Context context) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		UserOperationResponse res = getRegistrationResponse(input);
		return new GatewayResponse(res.toString(), headers, 200);
	}

	private UserOperationResponse getRegistrationResponse(RegistrationRequest input) {
		UserOperator creator = new UserOperator();
		int creationCode = creator.createUser(input);
		UserOperationResponse response = new UserOperationResponse();
		response.setResponseCode(creationCode);
		response.setResponseText("SUCCESS");
		return response;
	}
}