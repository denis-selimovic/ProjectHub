package ba.unsa.etf.nwt.taskservice.client.utility;

import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;

public class Utility {
    public static String getAuthHeader(ResourceOwner resourceOwner) {
        return "Bearer " + resourceOwner.getAccessToken();
    }
}
