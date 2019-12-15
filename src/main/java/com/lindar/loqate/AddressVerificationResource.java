package com.lindar.loqate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lindar.loqate.model.AddressDescription;
import com.lindar.loqate.model.FindRequest;
import com.lindar.loqate.model.FullAddress;
import com.lindar.loqate.util.LoqateConstants;
import com.lindar.loqate.util.RequestFactory;
import com.lindar.wellrested.vo.Result;
import com.lindar.wellrested.vo.ResultBuilder;
import com.lindar.wellrested.vo.WellRestedResponse;
import lindar.acolyte.util.UrlAcolyte;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AddressVerificationResource extends BaseResource {

    private final RequestFactory requestFactory;
    private final String         key;

    public Result<List<AddressDescription>> find(FindRequest request) {
        String path = LoqateConstants.EMAIL_VERIFICATION_ENDPOINT;
        Map<String, String> params = new HashMap<>();
        params.put("Key", this.key);
        params.put("Text", urlEncode(request.getText()));
        params.put("IsMiddleware", BooleanUtils.toString(request.isMiddleware(), "True", "False"));
        params.put("Container", urlEncode(request.getContainer()));
        params.put("Origin", urlEncode(request.getOrigin()));
        params.put("Countries", String.join(",", request.getCountries()));
        params.put("Limit", String.valueOf(request.getLimit()));
        params.put("Language", request.getLanguage());

        path = UrlAcolyte.addParams(path, params);

        WellRestedResponse response = requestFactory.makeRequest(path);
        return castToTypeArray(response.getServerResponse(), AddressDescription.class);
    }

    public Result<FullAddress> retrieve(String id) {
        return retrieve(id, new ArrayList<>());
    }

    public Result<FullAddress> retrieve(String id, List<String> fieldFormats) {
        String path = LoqateConstants.ADDRESS_VERIFICATION_RETRIEVE_ENDPOINT;
        Map<String, String> params = new HashMap<>();
        params.put("Key", this.key);
        params.put("Id", urlEncode(id));

        if (fieldFormats != null && !fieldFormats.isEmpty()) {
            if (fieldFormats.size() > 20) {
                log.warn("retrieve: only 20 field formats can be specified, you have specified {}. Only the first 20 will be taken into account", fieldFormats.size());
                fieldFormats = fieldFormats.subList(0, 19);
            }

            for (int fieldIndex = 0; fieldIndex < fieldFormats.size(); fieldIndex++) {
                params.put("Field" + fieldIndex + "Format", fieldFormats.get(fieldIndex));
            }
        }

        path = UrlAcolyte.addParams(path, params);

        WellRestedResponse response = requestFactory.makeRequest(path);
        Result<List<FullAddress>> listResult = castToTypeArray(response.getServerResponse(), FullAddress.class);

        return listResult.filter(data -> data != null && !data.isEmpty())
                         .map(data -> data.get(0));
    }

    private <T> Result<List<T>> castToTypeArray(String response, Class<T> type) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

        if (!jsonObject.has("Items")) {
            return ResultBuilder.failed("No items found");
        }

        JsonArray items = jsonObject.getAsJsonArray("Items");

        if (items.size() == 0) {
            return ResultBuilder.<List<T>>successful().build();
        }

        if (items.size() == 1) {
            JsonObject firstElement = items.get(0).getAsJsonObject();
            if (firstElement.has("Error")) {
                return ResultBuilder.<List<T>>failed()
                        .msg(firstElement.get("Description").getAsString())
                        .code(firstElement.get("Error").getAsString())
                        .build();
            }
        }

        Type typeToken = TypeToken.getParameterized(ArrayList.class, type).getType();
        List<T> results = new Gson().fromJson(items, typeToken);
        return ResultBuilder.successful(results);
    }
}
