package com.lindar.loqate;

import com.lindar.loqate.model.AddressDescription;
import com.lindar.loqate.model.FindRequest;
import com.lindar.loqate.model.FullAddress;
import com.lindar.loqate.util.RequestFactory;
import com.lindar.wellrested.vo.Result;
import com.lindar.wellrested.vo.WellRestedResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AddressVerificationResourceTest {

    private final LoqateClient client = LoqateClient.of(System.getProperty("LOQATE_KEY"));

    @Mock
    private RequestFactory requestFactory;
    private LoqateClient   clientMocked;

    @Before
    public void setup() {
        clientMocked = LoqateClient.of(System.getProperty("LOQATE_KEY"), requestFactory);
    }

    @Test
    public void testFind() {
        FindRequest request = FindRequest.builder().text("wr5 3da").build();
        Result<List<AddressDescription>> response = client.addressVerification().find(request);
        assertNotNull(response.getData());
    }

    @Test
    public void findAddress_whenSuccessfulResponse_shouldParseDataResponse() {
        WellRestedResponse response = new WellRestedResponse();
        response.setServerResponse(
                "{\"Items\":[{\"Id\":\"GB|RM|B|26772356\",\"Type\":\"Address\",\"Text\":\"Lock View, Basin Road\",\"Highlight\":\"\",\"Description\":\"Worcester, WR5 3DA\"},{\"Id\":\"GB|RM|B|53327590\",\"Type\":\"Address\",\"Text\":\"Lock Keepers Cottage, Basin Road\",\"Highlight\":\"\",\"Description\":\"Worcester, WR5 3DA\"},{\"Id\":\"GB|RM|B|52509479\",\"Type\":\"Address\",\"Text\":\"Loqate, Waterside, Basin Road\",\"Highlight\":\"\",\"Description\":\"Worcester, WR5 3DA\"}]}");
        Mockito.when(requestFactory.makeRequest(any())).thenReturn(response);

        FindRequest request = FindRequest.builder().text("wr5 3da").build();
        Result<List<AddressDescription>> result = clientMocked.addressVerification().find(request);
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(3, result.getData().size());
    }

    @Test
    public void findAddress_whenErrorResponse_shouldParseErrorResponse() {
        WellRestedResponse response = new WellRestedResponse();
        response.setServerResponse(
                "{\"Items\":[{\"Error\":\"2\",\"Description\":\"Unknown key\",\"Cause\":\"The key you are using to access the service was not found.\",\"Resolution\":\"Please check that the key is correct. It should be in the form AA11-AA11-AA11-AA11.\"}]}");
        Mockito.when(requestFactory.makeRequest(any())).thenReturn(response);

        FindRequest request = FindRequest.builder().text("wr5 3da").build();
        Result<List<AddressDescription>> result = clientMocked.addressVerification().find(request);
        assertTrue(result.isFailed());
        assertEquals("Unknown key", result.getMsg());
    }

    @Test
    public void retrieveAddress_whenSuccessfulResponse_shouldParseDataResponse() {
        WellRestedResponse response = new WellRestedResponse();
        response.setServerResponse(
                "{\"Items\":[{\"Id\":\"GB|RM|B|52509479\",\"DomesticId\":\"52509479\",\"Language\":\"ENG\",\"LanguageAlternatives\":\"ENG\",\"Department\":\"\",\"Company\":\"Loqate\",\"SubBuilding\":\"\",\"BuildingNumber\":\"\",\"BuildingName\":\"Waterside\",\"SecondaryStreet\":\"\",\"Street\":\"Basin Road\",\"Block\":\"\",\"Neighbourhood\":\"\",\"District\":\"\",\"City\":\"Worcester\",\"Line1\":\"Waterside\",\"Line2\":\"Basin Road\",\"Line3\":\"\",\"Line4\":\"\",\"Line5\":\"\",\"AdminAreaName\":\"Worcestershire\",\"AdminAreaCode\":\"\",\"Province\":\"Worcestershire\",\"ProvinceName\":\"Worcestershire\",\"ProvinceCode\":\"\",\"PostalCode\":\"WR5 3DA\",\"CountryName\":\"United Kingdom\",\"CountryIso2\":\"GB\",\"CountryIso3\":\"GBR\",\"CountryIsoNumber\":826,\"SortingNumber1\":\"94142\",\"SortingNumber2\":\"\",\"Barcode\":\"(WR53DA1PX)\",\"POBoxNumber\":\"\",\"Label\":\"Loqate\\nWaterside\\nBasin Road\\nWORCESTER\\nWR5 3DA\\nUNITED KINGDOM\",\"Type\":\"Commercial\",\"DataLevel\":\"Premise\",\"Field1\":\"Worcester\",\"Field2\":\"\",\"Field3\":\"\",\"Field4\":\"\",\"Field5\":\"\",\"Field6\":\"\",\"Field7\":\"\",\"Field8\":\"\",\"Field9\":\"\",\"Field10\":\"\",\"Field11\":\"\",\"Field12\":\"\",\"Field13\":\"\",\"Field14\":\"\",\"Field15\":\"\",\"Field16\":\"\",\"Field17\":\"\",\"Field18\":\"\",\"Field19\":\"\",\"Field20\":\"\"}]}");
        Mockito.when(requestFactory.makeRequest(any())).thenReturn(response);

        Result<FullAddress> result = clientMocked.addressVerification().retrieve("GB|RM|B|52509479");
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
    }
}