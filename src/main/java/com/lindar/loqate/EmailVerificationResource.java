package com.lindar.loqate;

import com.lindar.loqate.model.EmailVerificationItems;
import com.lindar.loqate.model.EmailVerificationResponse;
import com.lindar.loqate.util.LoqateConstants;
import com.lindar.loqate.util.RequestFactory;
import com.lindar.wellrested.vo.Result;
import com.lindar.wellrested.vo.ResultBuilder;
import com.lindar.wellrested.vo.WellRestedResponse;
import lindar.acolyte.util.ListsAcolyte;
import lindar.acolyte.util.UrlAcolyte;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class EmailVerificationResource extends BaseResource {

    private final String         key;
    private final String         baseUrl;
    private final RequestFactory requestFactory;

    public Result<EmailVerificationResponse> verifyEmail(String email) {
        return verifyEmail(email, null);
    }

    public Result<EmailVerificationResponse> verifyEmail(String email, Integer timeout) {
        String path = UrlAcolyte.safeConcat(baseUrl, LoqateConstants.EMAIL_VERIFICATION_ENDPOINT);

        Map<String, String> params = new HashMap<>();
        params.put("Key", this.key);
        params.put("Email", urlEncode(sanitiseEmail(email)));
        if (timeout != null) {
            params.put("Timeout", timeout.toString());
        }

        path = UrlAcolyte.addParams(path, params);

        WellRestedResponse response = requestFactory.makeRequest(path);
        log.debug("Loqate Address Response: {}", response.getServerResponse());
        return handleResponse(response);
    }

    private String sanitiseEmail(String email) {
        return StringUtils.defaultString(email).trim();
    }

    private Result<EmailVerificationResponse> handleResponse(WellRestedResponse response) {
        try {
            EmailVerificationItems verificationResponseItems = response.fromJson().castTo(EmailVerificationItems.class);

            if (verificationResponseItems != null && ListsAcolyte.isNotEmpty(verificationResponseItems.getItems())) {
                EmailVerificationResponse verificationResponse = verificationResponseItems.getItems().get(0);
                if (verificationResponse != null && verificationResponse.getResponseCode() != null) {
                    return ResultBuilder.successful(verificationResponse);
                } else {
                    return ResultBuilder.<EmailVerificationResponse>failed().data(verificationResponse).build();
                }
            }
        } catch (Exception ex) {
            log.debug("handleResponse: ", ex);
        }
        return ResultBuilder.<EmailVerificationResponse>failed().build();
    }
}
